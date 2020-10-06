#!/usr/bin/perl
use warnings;
use strict;

my @instances = qw {1A/2KP50-11 1A/2KP50-50 1A/2KP50-92 1A/2KP100-50 1A/2KP500-41 1B/2KP100-1A 1B/2KP100-1C 1B/2KP200-1A 1B/2KP200-1C 1B/2KP300-1A 1B/2KP300-1C 1B/2KP400-1A 1B/2KP400-1C 1B/2KP500-1A 1B/2KP500-1C 1B/2KP100-1B 1B/2KP100-1D 1B/2KP200-1B 1B/2KP200-1D 1B/2KP300-1B 1B/2KP300-1D 1B/2KP400-1B 1B/2KP400-1D 1B/2KP500-1B 1B/2KP500-1D StrCorrelated/1S1W1 StrCorrelated/1S250W1 StrCorrelated/1S300W1 StrCorrelated/1S350W1 StrCorrelated/1S400W1 StrCorrelated/1S450W1 StrCorrelated/1S500W1 StrCorrelated/1S600W1 StrCorrelated/1S700W1 StrCorrelated/1S800W1 StrCorrelated/1S900W1 StrCorrelated/S1100W1 StrCorrelated/S1150W1 StrCorrelated/S1200W1 StrCorrelated/S1C50W01 Uncorrelated/F5050W01 Uncorrelated/F5050W03 Uncorrelated/F5050W05 Uncorrelated/F5050W07 Uncorrelated/F5050W09 Uncorrelated/K5050W01 Uncorrelated/K5050W03 Uncorrelated/K5050W05 Uncorrelated/K5050W07 Uncorrelated/K5050W09 Uncorrelated/F5050W02 Uncorrelated/F5050W04 Uncorrelated/F5050W06 Uncorrelated/F5050W08 Uncorrelated/F5050W10 Uncorrelated/K5050W02 Uncorrelated/K5050W04 Uncorrelated/K5050W06 Uncorrelated/K5050W08 Uncorrelated/K5050W10 WeakCorrelated/4W150W1 WeakCorrelated/4W1W1 WeakCorrelated/4W200W1 WeakCorrelated/4W250W1 WeakCorrelated/4W300W1 WeakCorrelated/4W350W1 WeakCorrelated/4W400W1 WeakCorrelated/4W450W1 WeakCorrelated/4W500W1 WeakCorrelated/4W600W1 WeakCorrelated/4W700W1 WeakCorrelated/4W800W1 WeakCorrelated/4W900W1 WeakCorrelated/W4100W1 WeakCorrelated/W4C50W01};

for my $instance (@instances) {
  my $shortInstance = $instance;
  $shortInstance=~ s/\//\-/;
  for my $objective (1..2) {
    my $script = qq{postscript("boxplot-${shortInstance}-Objective${objective}.eps", horizontal=FALSE, height=8, width=16, pointsize=12)
data1<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/KP/SingleObjective/eES/${instance}/objective${objective}.csv")
data2<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/KP/SingleObjective/gGA/${instance}/objective${objective}.csv")
data3<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/KP/MultiObjective/MOEAD/${instance}/objective${objective}.csv")
data4<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/KP/MultiObjective/NSGAII/${instance}/objective${objective}.csv")
data5<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/KP/MultiObjective/SMSEMOA/${instance}/objective${objective}.csv")
dataM<-matrix(c(data1,data2,data3,data4,data5), 100)
library("Rlab")
bplot(dataM, space = 0.6, labels = c("eES", "gGA", "MOEAD", "NSGAII", "SMSEMOA"), ylab = "Objective value", main = "KP - ${shortInstance} - Objective ${objective} - 100 rep.")
dev.off()};

    open FILE, "> boxplot-KP-${shortInstance}-Objective${objective}.R";
    print FILE $script;
    close FILE;
  }
}
