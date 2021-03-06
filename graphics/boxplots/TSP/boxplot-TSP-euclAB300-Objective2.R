postscript("boxplot-euclAB300-Objective2.eps", horizontal=FALSE, pointsize=16)
data1<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/eES/euclAB300/objective2.csv")
data2<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/SingleObjective/gGA/euclAB300/objective2.csv")
data3<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/MOEAD/euclAB300/objective2.csv")
data4<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/NSGAII/euclAB300/objective2.csv")
data5<-scan("/home/edusegre/Descargas/SOvsMO/RESULT/TSP/MultiObjective/SMSEMOA/euclAB300/objective2.csv")
dataM<-matrix(c(data1,data2,data3,data4,data5), 100)
library("Rlab")
bplot(dataM, space = 0.6, labels = c("eES", "gGA", "MOEAD", "NSGAII", "SMSEMOA"), ylab = "Objective value", main = "TSP - euclAB300 - Objective 2 - 100 rep.")
dev.off()