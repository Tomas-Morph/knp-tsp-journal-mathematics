#!/usr/bin/perl
use warnings;
use strict;

# Multi-objective and single-objective approaches
my @multi_algs = qw{MultiObjective/MOEAD MultiObjective/NSGAII MultiObjective/SMSEMOA};
my @single_algs = qw{SingleObjective/eES SingleObjective/gGA};

# TSP instances
my @instances = qw {clusAB100 clusAB500 euclAB300 kroAB100 kroAB150 kroAB300 kroAB500 kroAC100 kroBC100 kroCD100 clusAB300 euclAB100 euclAB500 kroAB1000 kroAB200 kroAB400 kroAB750 kroAD100 kroBD100};

# Objective number 
my $obj = 2;

for my $instance (@instances) {
  print "${instance}\n";

  # Calcular el mejor algoritmo multi-objetivo respecto a la mediana
  my $best_median = 99999999999999999999999;
  my $best_multi_alg;
  for my $multi_alg (@multi_algs) {
    my $filename = "/home/edusegre/Descargas/SOvsMO/RESULT/TSP/${multi_alg}/${instance}/objective${obj}.csv";
    open(my $file, '<', $filename) or die "Unable to open file, $!";
    my @values = <$file>;
    close($file);
    chomp(@values);
    my $current_median = getMedian(\@values);
    if ($current_median < $best_median) {
      $best_median = $current_median;
      $best_multi_alg = $multi_alg;
    }
  }
  print "$best_multi_alg\n";

  # Calcular el mejor algoritmo mono-objetivo respecto a la mediana
  $best_median = 99999999999999999999999;
  my $best_single_alg;
  for my $single_alg (@single_algs) {
    my $filename = "/home/edusegre/Descargas/SOvsMO/RESULT/TSP/${single_alg}/${instance}/objective${obj}.csv";
    open(my $file, '<', $filename) or die "Unable to open file, $!";
    my @values = <$file>;
    close($file);
    chomp(@values);
    my $current_median = getMedian(\@values);
    if ($current_median < $best_median) {
      $best_median = $current_median;
      $best_single_alg = $single_alg;
    }
  }
  print "$best_single_alg\n";

  # Calcular el resultado de la comparativa estadística entre el mejor multi-objetivo y el mejor mono-objetivo
  my @stat_result = `./statisticalTests-TSP.pl 100 /home/edusegre/Descargas/SOvsMO/RESULT/TSP/${best_multi_alg}/${instance}/objective${obj}.csv /home/edusegre/Descargas/SOvsMO/RESULT/TSP/${best_single_alg}/${instance}/objective${obj}.csv`;

  # Imprimir si ha sido mejor un MultiObjective o un SingleObjective, desde el punto de vista estadístico
  if ($stat_result[1] =~ /MultiObjective/) {
    print "M\n";
  } elsif ($stat_result[1] =~ /SingleObjective/) {
    print "S\n";
  } else {
    print "Analyse\n";
  }
  print "----------------------------------\n"
}

# Función para calcular la mediana
sub getMedian {
  my $values_ref = shift;
  my @values = @{$values_ref};
  my $mid = int @values / 2;
  my @sorted_values = sort by_number @values;
  my $median;
  if (@values % 2) {
    $median = $sorted_values[$mid];
  } else {
    $median = ($sorted_values[$mid - 1] + $sorted_values[$mid]) / 2;
  } 
  return $median;
}

# Función para ser utilizada a la hora de ordenar una lista de valores numéricos
sub by_number {
  if ($a < $b) { -1 } elsif ($a > $b) { 1 } else { 0 }
}
