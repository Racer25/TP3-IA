################################################################
# Makefile for preparing/moving/installing the binary distribution of
# SWI-Prolog and/or XPCE
#
# Author: Jan Wielemaker
# E-mail: J.Wielemaker@cs.vu.nl
#
# Targets
#	make		(Re)makes SWI-Prolog startup file and XPCE
#			precompiled Prolog files (QLF-files).
#	make clean	Remove XPCE QLF-files.  All should work
#			without them.  Without QLF files loading
#			is slower but development is better and
#			relocating the system is more robust.
################################################################

MAKE=make
SWIPL=swipl
PLWINARG=-F swipl-win
PL=bin/x64-win64/$(PLWINNAME)
PLARCH=x64-win64
INSTALL_PLARCH=
EXEEXT=.exe
.SUFFIXES: .pl .qlf

BPL=		bin/$(INSTALL_PLARCH)/$(SWIPL)$(EXEEXT) $(PLWINARG)
PWD=`pwd`

# Make this boot64 for 64-bit processors

STARTUP=	boot32.prc
BOOTPLS=	boot/apply.pl boot/autoload.pl \
		boot/bags.pl boot/dwim.pl \
		boot/history.pl boot/init.pl \
		boot/load.pl boot/parms.pl \
		boot/qlf.pl boot/syspred.pl boot/toplevel.pl \
		boot/topvars.pl boot/predopts.pl
LIBQLF=		library/cql/cql.qlf $(LIBQLF)

all:		$(STARTUP) xpce-qlfs

$(STARTUP):	$(BOOTPLS)
		$(BPL) -f none -O -o $@ -b boot/init.pl

xpce-qlfs:
		@PLBASE=$(PWD); \
		if [ -d xpce ]; then \
		   echo $(MAKE) PL="$$PLBASE/$(BPL)"; \
                   (cd xpce/prolog/lib; $(MAKE) PL="$$PLBASE/$(BPL)"); \
		fi

.pl.qlf:
		if [ -f $*.pl ]; then \
		  $(BPL) -g 'qcompile($*),halt' -t 'halt(1)'; \
		fi

clean:
		rm -f `find . -name '*.qlf' -print`
