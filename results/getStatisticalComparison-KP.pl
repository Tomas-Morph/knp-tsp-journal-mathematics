#!/usr/bin/perl
use warnings;
use strict;

# Multi-objective and single-objective approaches
my @multi_algs = qw{MultiObjective/MOEAD MultiObjective/NSGAII MultiObjective/SMSEMOA};
my @single_algs = qw{SingleObjective/eES SingleObjective/gGA};

# KP instances
my @instances = qw {1A/2KP50-11 1A/2KP50-50 1A/2KP50-92 1A/2KP100-50 1A/2KP500-41 1B/2KP100-1A 1B/2KP100-1C 1B/2KP200-1A 1B/2KP200-1C 1B/2KP300-1A 1B/2KP300-1C 1B/2KP400-1A 1B/2KP400-1C 1B/2KP500-1A 1B/2KP500-1C 1B/2KP100-1B 1B/2KP100-1D 1B/2KP200-1B 1B/2KP200-1D 1B/2KP300-1B 1B/2KP300-1D 1B/2KP400-1B 1B/2KP400-1D 1B/2KP500-1B 1B/2KP500-1D StrCorrelated/1S1W1 StrCorrelated/1S250W1 StrCorrelated/1S300W1 StrCorrelated/1S350W1 StrCorrelated/1S400W1 StrCorrelated/1S450W1 StrCorrelated/1S500W1 StrCorrelated/1S600W1 StrCorrelated/1S700W1 StrCorrelated/1S800W1 StrCorrelated/1S900W1 StrCorrelated/S1100W1 StrCorrelated/S1150W1 StrCorrelated/S1200W1 StrCorrelated/S1C50W01 Uncorrelated/F5050W01 Uncorrelated/F5050W03 Uncorrelated/F5050W05 Uncorrelated/F5050W07 Uncorrelated/F5050W09 Uncorrelated/K5050W01 Uncorrelated/K5050W03 Uncorrelated/K5050W05 Uncorrelated/K5050W07 Uncorrelated/K5050W09 Uncorrelated/F5050W02 Uncorrelated/F5050W04 Uncorrelated/F5050W06 Uncorrelated/F5050W08 Uncorrelated/F5050W10 Uncorrelated/K5050W02 Uncorrelated/K5050W04 Uncorrelated/K5050W06 Uncorrelated/K5050W08 Uncorrelated/K5050W10 WeakCorrelated/4W150W1 WeakCorrelated/4W1W1 WeakCorrelated/4W200W1 WeakCorrelated/4W250W1 WeakCorrelated/4W300W1 WeakCorrelated/4W350W1 WeakCorrelated/4W400W1 WeakCorrelated/4W450W1 WeakCorrelated/4W500W1 WeakCorrelated/4W600W1 WeakCorrelated/4W700W1 WeakCorrelated/4W800W1 WeakCorrelated/4W900W1 WeakCorrelated/W4100W1 WeakCorrelated/W4C50W01};

# Objective number 
my $obj = 2;

for my $instance (@instances) {
  print "${instance}\n";

  # Calcular el mejor algoritmo multi-objetivo respecto a la mediana
  my $best_median = -99999999999999999999999;
  my $best_multi_alg;
  for my $multi_alg (@multi_algs) {
    my $filename = "/home/edusegre/Descargas/SOvsMO/RESULT/KP/${multi_alg}/${instance}/objective${obj}.csv";
    open(my $file, '<', $filename) or die "Unable to open file, $!";
    my @values = <$file>;
    close($file);
    chomp(@values);
    my $current_median = getMedian(\@values);
    if ($current_median > $best_median) {
      $best_median = $current_median;
      $best_multi_alg = $multi_alg;
    }
  }
  print "$best_multi_alg\n";

  # Calcular el mejor algoritmo mono-objetivo respecto a la mediana
  $best_median = -99999999999999999999999;
  my $best_single_alg;
  for my $single_alg (@single_algs) {
    my $filename = "/home/edusegre/Descargas/SOvsMO/RESULT/KP/${single_alg}/${instance}/objective${obj}.csv";
    open(my $file, '<', $filename) or die "Unable to open file, $!";
    my @values = <$file>;
    close($file);
    chomp(@values);
    my $current_median = getMedian(\@values);
    if ($current_median > $best_median) {
      $best_median = $current_median;
      $best_single_alg = $single_alg;
    }
  }
  print "$best_single_alg\n";

  # Calcular el resultado de la comparativa estadística entre el mejor multi-objetivo y el mejor mono-objetivo
  my @stat_result = `./statisticalTests-KP.pl 100 /home/edusegre/Descargas/SOvsMO/RESULT/KP/${best_multi_alg}/${instance}/objective${obj}.csv /home/edusegre/Descargas/SOvsMO/RESULT/KP/${best_single_alg}/${instance}/objective${obj}.csv`;

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
