mpl_top    = 0.9 #inch  outer top margin, title goes here
mpl_bot    = 0.9 #inch  outer bottom margin, x label goes here
mpl_left   = 0.4 #inch  outer left margin, y label goes here
mpl_right  = 0.2 #inch  outer right margin, y2 label goes here
mpl_height = 1.5 #inch  height of individual plots
mpl_width  = 2.5 #inch  width of individual plots
mpl_dx     = 0.5 #inch  inter-plot horizontal spacing
mpl_dy     = 0.5 #inch  inter-plot vertical spacing
mpl_ny     = 6   #number of rows
mpl_nx     = 3   #number of columns

set key bottom right
# calculate full dimensions
xsize = mpl_left+mpl_right+(mpl_width*mpl_nx)+(mpl_nx-1)*mpl_dx
ysize = mpl_top+mpl_bot+(mpl_ny*mpl_height)+(mpl_ny-1)*mpl_dy

# Remove border on top and right.
# These borders are useless and make it harder
# to see plotted lines near the border.
# Also, put it in grey; no need for so much emphasis on a border.


# Line style for grid
# set style line 81 lt 3  # dashed
# set style line 81 lt rgb '#808080' lw 0.5  # grey
# set grid back linestyle 81
# set border 3 back linestyle 80 


set border 3 back linestyle 80 
set xtics nomirror
set ytics nomirror

# placement functions
#   rows are numbered from bottom to top
bot(n) = (mpl_bot+(n-1)*mpl_height+(n-1)*mpl_dy)/ysize
top(n)  = 1-((mpl_top+(mpl_ny-n)*(mpl_height+mpl_dy))/ysize)
#   columns are numbered from left to right
left(n) = (mpl_left+(n-1)*mpl_width+(n-1)*mpl_dx)/xsize
right(n)  = 1-((mpl_right+(mpl_nx-n)*(mpl_width+mpl_dx))/xsize)

#set terminal postscript eps size 5,3.62 enhanced color font 'Helvetica,20' lw 30

set terminal postscript eps size 5,3.62 enhanced color size xsize,ysize "Helvetica" 10

set encoding iso_8859_1
set tics scale 0.7

set output 'KNP.eps'

set offsets
set autoscale fix
set size 1,1
set key font ",12"

set title font "Arial-Bold,13"
set xlabel font ",12"
set ylabel font ",12"
set y2label font "Arial-Bold,13"

# define x-axis settings for all subplots
set xlabel "Number of evaluations (x10^{3})"
set ylabel "Mean objective value"
#set format x ''
set xtics nomirror
set ytics nomirror
##set yrange [40:100]

# Line styles: try to pick pleasing colors, rather
# than strictly primary colors or hard-to-see colors
# like gnuplot's default yellow.  Make the lines thick
# so they're easy to see in small plots in papers.
set style line 1 lt 1
set style line 2 lt 1
set style line 3 lt 1
set style line 4 lt 1
set style line 5 lt 1
set style line 1 lt rgb '#21618C' lw 3 pt 7
set style line 2 lt rgb '#9B59B6' lw 3 pt 7
set style line 3 lt rgb '#E74C3C' lw 3 pt 7
set style line 4 lt rgb '#27AE60' lw 3 pt 9
set style line 5 lt rgb '#F4D03F' lw 3 pt 9

set pointsize 0.6

# start plotting
set multiplot

#-----------------------------------------------
# subplot  1-6
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(6)
set bmargin at screen bot(6)

set title 'K5050W01'
#set format y ""           # no tic labels
set y2label ""
#set yrange [95.1:100]
#set yrange [40:100]
plot "objective1/K5050W01.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/K5050W01.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/K5050W01.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/K5050W01.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/K5050W01.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-6
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(6)
set bmargin at screen bot(6)

set title 'K5050W05'

#set format y ""           # no tic labels
set y2label ""
#set yrange [94.8:100]
#set yrange [40:100]
plot "objective1/K5050W05.csv" using 1:2  title 'NSGA-II' axes x1y2 with  histeps ls 1 pointinterval 5,\
    "objective1/K5050W05.csv" using 1:3  title 'MOEA/D' axes x1y2 with  histeps ls 2 pointinterval 5,\
    "objective1/K5050W05.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with  histeps ls 3 pointinterval 5,\
    "objective1/K5050W05.csv" using 1:5  title 'gGA' axes x1y2 with  histeps ls 4 pointinterval 5,\
    "objective1/K5050W05.csv" using 1:6  title 'eES' axes x1y2 with  histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-6
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(6)
set bmargin at screen bot(6)

set title 'K5050W10'
set y2label "Objective 1"
#set yrange [40:100]
#set yrange [95.1:100]
#set format y ""           # no tic labels
plot "objective1/K5050W10.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/K5050W10.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/K5050W10.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/K5050W10.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/K5050W10.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  1-5
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(5)
set bmargin at screen bot(5)

set title 'K5050W01'
#set format y ""           # no tic labels
set y2label ""

#set yrange [69.4:100]
#set yrange [40:100]
plot "objective2/K5050W01.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/K5050W01.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/K5050W01.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/K5050W01.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/K5050W01.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-5
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(5)
set bmargin at screen bot(5)

set title 'K5050W05'

#set format y ""           # no tic labels
set y2label ""
#set yrange [67.5:100]
#set yrange [40:100]
plot "objective2/K5050W05.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/K5050W05.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/K5050W05.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/K5050W05.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/K5050W05.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-5
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(5)
set bmargin at screen bot(5)

set title 'K5050W10'
set y2label "Objective 2"

#set format y ""           # no tic labels
#set yrange [64.8:100]
#set yrange [40:100]
plot "objective2/K5050W10.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/K5050W10.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/K5050W10.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/K5050W10.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/K5050W10.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  1-4
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(4)
set bmargin at screen bot(4)

set title '4W300W1'
#set format y ""           # no tic labels
set y2label ""

#set yrange [71.9:100]
#set yrange [40:100]
plot "objective1/4W300W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/4W300W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/4W300W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/4W300W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/4W300W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-4
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(4)
set bmargin at screen bot(4)

set title '4W600W1'

#set format y ""           # no tic labels
set y2label ""
#set yrange [67.8:100]
#set yrange [40:100]
plot "objective1/4W600W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/4W600W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/4W600W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/4W600W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/4W600W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-4
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(4)
set bmargin at screen bot(4)

set title '4W900W1'
set y2label "Objective 1"

#set format y ""           # no tic labels
#set yrange [67:100]
#set yrange [40:100]
plot "objective1/4W900W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/4W900W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/4W900W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/4W900W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/4W900W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  1-3
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(3)
set bmargin at screen bot(3)

set title '4W300W1'
#set format y ""           # no tic labels
set y2label ""

#set yrange [72.3:100]
#set yrange [40:100]
plot "objective2/4W300W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/4W300W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/4W300W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/4W300W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/4W300W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-3
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(3)
set bmargin at screen bot(3)

set title '4W600W1'

#set format y ""           # no tic labels
set y2label ""
#set yrange [67:100]
#set yrange [40:100]
plot "objective2/4W600W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/4W600W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/4W600W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/4W600W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/4W600W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-3
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(3)
set bmargin at screen bot(3)

set title '4W900W1'
set y2label "Objective 2"

#set format y ""           # no tic labels
#set yrange [67.4:100]
#set yrange [40:100]
plot "objective2/4W900W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/4W900W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/4W900W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/4W900W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/4W900W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  1-2
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(2)
set bmargin at screen bot(2)

set title '1S300W1'
#set format y ""           # no tic labels
set y2label ""

#set yrange [83.9:100]
#set yrange [40:100]
plot "objective1/1S300W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/1S300W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/1S300W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/1S300W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/1S300W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-2
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(2)
set bmargin at screen bot(2)

set title '1S600W1'

#set format y ""           # no tic labels
set y2label ""
#set yrange [85.2:100]
#set yrange [40:100]
plot "objective1/1S600W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/1S600W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/1S600W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/1S600W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/1S600W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-2
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(2)
set bmargin at screen bot(2)

set title '1S900W1'
set y2label "Objective 1"

#set format y ""           # no tic labels
#set yrange [84.6:100]
#set yrange [40:100]
plot "objective1/1S900W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective1/1S900W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective1/1S900W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective1/1S900W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective1/1S900W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  1-1
#  set horizontal margins for first column
set lmargin at screen left(1)
set rmargin at screen right(1)
#  set horizontal margins for third row (top)
set tmargin at screen top(1)
set bmargin at screen bot(1)

set title '1S300W1'
#set format y ""           # no tic labels
set y2label ""

#set yrange [86.6:100]
#set yrange [40:100]
plot "objective2/1S300W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/1S300W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/1S300W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/1S300W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/1S300W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;

#-----------------------------------------------
# subplot  2-1
#  set horizontal margins for second column
set lmargin at screen left(2)
set rmargin at screen right(2)
#  set horizontal margins for third row (top)
set tmargin at screen top(1)
set bmargin at screen bot(1)

set title '1S600W1'

#set format y ""           # no tic labels
set y2label ""
#set yrange [84:100]
#set yrange [40:100]
plot "objective2/1S600W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/1S600W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/1S600W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/1S600W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/1S600W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;
#-----------------------------------------------
# subplot  3-1
#  set horizontal margins for first column
set lmargin at screen left(3)
set rmargin at screen right(3)
#  set horizontal margins for second row (middle)
set tmargin at screen top(1)
set bmargin at screen bot(1)

set title '1S900W1'
set y2label "Objective 2"

#smooth acsplines
#smooth bezier
#smooth csplines
#smooth sbezier
#smooth unique
#smooth frequency

#set format y ""           # no tic labels
#set yrange [83.7:100]
#set yrange [40:100]
plot "objective2/1S900W1.csv" using 1:2  title 'NSGA-II' axes x1y2 with histeps ls 1 pointinterval 5,\
    "objective2/1S900W1.csv" using 1:3  title 'MOEA/D' axes x1y2 with histeps ls 2 pointinterval 5,\
    "objective2/1S900W1.csv" using 1:4  title 'SMS-EMOA' axes x1y2 with histeps ls 3 pointinterval 5,\
    "objective2/1S900W1.csv" using 1:5  title 'gGA' axes x1y2 with histeps ls 4 pointinterval 5,\
    "objective2/1S900W1.csv" using 1:6  title 'eES' axes x1y2 with histeps ls 5 pointinterval 5
;