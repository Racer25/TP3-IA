/*  $Id$

    Part of SWI-Prolog

    Author:        Jan Wielemaker & Steve Prior
    E-mail:        J.Wielemaker@vu.nl
    WWW:           http://www.swi-prolog.org
    Copyright (C): 1985-2011, University of Amsterdam
			      VU University Amsterdam

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

    As a special exception, if you link this library with other files,
    compiled with a Free Software compiler, to produce an executable, this
    library does not by itself cause the resulting executable to be covered
    by the GNU General Public License. This exception does not however
    invalidate any other reasons why the executable file might be covered by
    the GNU General Public License.
*/

:- module(prolog_server,
	  [ prolog_server/2		% +Port, +Options
	  ]).

:- use_module(library(socket)).

%%	prolog_server(?Port, +Options)
%
%	Create a TCP/IP based server  on  the   given  Port,  so you can
%	telnet into Prolog and run an  interactive session. This library
%	is intended to provide access for   debugging  and management of
%	embedded servers.
%
%	Currently defined options are:
%
%		* allow(IP)
%		Allow access from IP, a term of the format ip(A,B,C,D).
%		Multiple of such terms can exist and access is granted
%		if the peer IP address unifies to one of them.  If no
%		allow option is provided access is only granted from
%		ip(127,0,0,1) (localhost).
%
%	For example:
%
%		==
%		?- prolog_server(4000, []).
%
%		% telnet localhost 4000
%		Welcome to the SWI-Prolog server on thread 3
%
%		1 ?-
%		==
%
%	@bug As the connection does not involve a terminal, command history
%	and completion are not provided. Neither are interrupts
%	(Control-C).  To terminate the Prolog shell one must enter the
%	command "end_of_file."


prolog_server(Port, Options) :-
	tcp_socket(ServerSocket),
	tcp_setopt(ServerSocket, reuseaddr),
	tcp_bind(ServerSocket, Port),
	tcp_listen(ServerSocket, 5),
	thread_create(server_loop(ServerSocket, Options), _,
		      [ alias(prolog_server)
		      ]).

server_loop(ServerSocket, Options) :-
	tcp_accept(ServerSocket, Slave, Peer),
	tcp_open_socket(Slave, InStream, OutStream),
	set_stream(InStream, close_on_abort(false)),
	set_stream(OutStream, close_on_abort(false)),
	tcp_host_to_address(Host, Peer),
	(   Postfix = []
	;   between(2, 1000, Num),
	    Postfix = [-, Num]
	),
	atomic_list_concat(['client@', Host | Postfix], Alias),
	catch(thread_create(
		  service_client(InStream, OutStream, Peer, Options),
		  _,
		  [ alias(Alias)
		  ]),
	      error(permission_error(create, thread, Alias), _),
	      fail), !,
	server_loop(ServerSocket, Options).

service_client(InStream, OutStream, Peer, Options) :-
	allow(Peer, Options), !,
	thread_self(Id),
	set_prolog_IO(InStream, OutStream, OutStream),
	set_stream(InStream, tty(true)),
	set_prolog_flag(tty_control, false),
	current_prolog_flag(encoding, Enc),
	set_stream(user_input, encoding(Enc)),
	set_stream(user_output, encoding(Enc)),
	set_stream(user_error, encoding(Enc)),
	set_stream(user_input, newline(detect)),
	set_stream(user_output, newline(dos)),
	set_stream(user_error, newline(dos)),
	format(user_error,
	       'Welcome to the SWI-Prolog server on thread ~w~n~n',
	       [Id]),
	call_cleanup(prolog,
		     ( close(InStream),
		       close(OutStream),
		       thread_detach(Id))).
service_client(InStream, OutStream, _, _):-
	thread_self(Id),
	format(OutStream, 'Go away!!~n', []),
	close(InStream),
	close(OutStream),
	thread_detach(Id).


allow(Peer, Options) :-
	(   member(allow(Allow), Options)
	*-> Peer = Allow,
	    !
	;   Peer = ip(127,0,0,1)
	).
