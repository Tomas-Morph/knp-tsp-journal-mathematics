#!/usr/bin/perl
use warnings;
use strict;

my @instances = qw {clusAB100 clusAB500 euclAB300 kroAB100 kroAB150 kroAB300 kroAB500 kroAC100 kroBC100 kroCD100 clusAB300 euclAB100 euclAB500 kroAB1000 kroAB200 kroAB400 kroAB750 kroAD100 kroBD100};

for my $instance (@instances) {
  for my $objective (1..2) {
    my $script = qq{postscript("boxplot-${instance}-Objective${objective}.eps", horizontal=FALSE, pointsize=16)
data1<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/eES/${instance}/objective${objective}.csv")
data2<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/gGA/${instance}/objective${objective}.csv")
data3<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/MOEAD/${instance}/objective${objective}.csv")
data4<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/NSGAII/${instance}/objective${objective}.csv")
data5<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/SMSEMOA/${instance}/objective${objective}.csv")
dataM<-matrix(c(data1,data2,data3,data4,data5), 100)
library("Rlab")
bplot(dataM, space = 0.6, labels = c("eES", "gGA", "MOEAD", "NSGAII", "SMSEMOA"), ylab = "Objective value", main = "TSP - ${instance} - Objective ${objective} - 100 rep.")
dev.off()};

    open FILE, "> boxplot-TSP-${instance}-Objective${objective}.R";
    print FILE $script;
    close FILE;
  }
}
