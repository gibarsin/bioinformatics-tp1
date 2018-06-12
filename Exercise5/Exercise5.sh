#!/bin/bash
FASTA_FILE=$1

if [ ! -z "$2" ]
	then
		CMD=showorfs
	else
		CMD=getorf
fi
$CMD -sequence $FASTA_FILE -snucleotide1 -auto
patmatmotifs -sequence $FASTA_FILE -auto

# Documentación
# http://emboss.sourceforge.net/apps/release/6.6/emboss/apps/prosextract.html
# ftp://ftp.expasy.org/databases/prosite/README