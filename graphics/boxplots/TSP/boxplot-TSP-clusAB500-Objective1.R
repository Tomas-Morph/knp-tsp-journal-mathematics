postscript("boxplot-clusAB500-Objective1.eps", horizontal=FALSE, pointsize=16)
data1<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/eES/clusAB500/objective1.csv")
data2<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/gGA/clusAB500/objective1.csv")
data3<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/MOEAD/clusAB500/objective1.csv")
data4<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/NSGAII/clusAB500/objective1.csv")
data5<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/SMSEMOA/clusAB500/objective1.csv")
dataM<-matrix(c(data1,data2,data3,data4,data5), 100)
library("Rlab")
bplot(dataM, space = 0.6, labels = c("eES", "gGA", "MOEAD", "NSGAII", "SMSEMOA"), ylab = "Objective value", main = "TSP - clusAB500 - Objective 1 - 100 rep.")
dev.off()