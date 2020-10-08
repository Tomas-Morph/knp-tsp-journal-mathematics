package org.uma.jmetal.util.experiment.util;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

/**
 * Class defining tasks for the execution of algorithms in parallel.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ExperimentAlgorithm<S extends Solution<?>, Result extends List<S>>  {
  private Algorithm<Result> algorithm;
  private String algorithmTag;
  private String problemTag;
  private String referenceParetoFront;
  private int runId ;

  /**
   * Constructor
   */
  public ExperimentAlgorithm(
          Algorithm<Result> algorithm,
          String algorithmTag,
          ExperimentProblem<S> problem,
          int runId) {
    this.algorithm = algorithm;
    this.algorithmTag = algorithmTag;
    this.problemTag = problem.getTag();

    this.referenceParetoFront = problem.getReferenceFront();
    this.runId = runId ;
  }

  public ExperimentAlgorithm(
          Algorithm<Result> algorithm,
          ExperimentProblem<S> problem,
          int runId) {

    this(algorithm,algorithm.getName(),problem,runId);

  }

  public void runAlgorithm(Experiment<?, ?> experimentData) {

    String outputDirectoryName = experimentData.getExperimentBaseDirectory()
//            + "/multiobjective"
    		+ "/data/"
            + algorithmTag
/*           + "/"
/*            + problemTag*/
            ;

    File outputDirectory = new File(outputDirectoryName);
    if (!outputDirectory.exists()) {
      boolean result = new File(outputDirectoryName).mkdirs();
      if (result) {
        JMetalLogger.logger.info("Creating " + outputDirectoryName);
      } else {
        JMetalLogger.logger.severe("Creating " + outputDirectoryName + " failed");
      }
    }

    String funFile = outputDirectoryName + "/FUN" + runId + ".tsv";
    String varFile = outputDirectoryName + "/VAR" + runId + ".tsv";
    JMetalLogger.logger.info(
            " Running algorithm: " + algorithmTag +
                    ", problem: " + problemTag +
                    ", run: " + runId +
                    ", funFile: " + funFile);
    

    
    
    
    
    /**/long initTime = System.currentTimeMillis();

    algorithm.run();
    
    /**/long computingTime = System.currentTimeMillis() - initTime ;
    /**/JMetalLogger.logger.info("Execution time: algorithm Name["+algorithmTag+"_"+problemTag+"_run"+runId+"] | Total time: [" + computingTime + "ms]" );
   
    
    
    
    
    
    String timeFile = experimentData.getExperimentBaseDirectory() + "/runTimeSummary.tsv";
	try{
	FileWriter w = new FileWriter(timeFile, true);
	BufferedWriter bw = new BufferedWriter(w);
	PrintWriter wr = new PrintWriter(bw);	
	wr.write(algorithmTag+" \t\t"+problemTag+"\t\t"+"run"+runId+"\t\t"+String.valueOf(computingTime)+"\n");
	wr.close();
	bw.close();
	}catch(IOException e){};

	
	
    
    Result population = algorithm.getResult();

	new SolutionListOutput(population)
	// for TSP comment this line
	.setObjectiveMinimizingObjectiveList(Arrays.asList(false, false))
	.setSeparator("\t")
    .setVarFileOutputContext(new DefaultFileOutputContext(varFile))
    .setFunFileOutputContext(new DefaultFileOutputContext(funFile))
	.print();
    
//    new SolutionListOutput(population)
//            .setSeparator("\t")
//            .setVarFileOutputContext(new DefaultFileOutputContext(varFile))
//            .setFunFileOutputContext(new DefaultFileOutputContext(funFile))
//            .print();
    
  }

  public Algorithm<Result> getAlgorithm() {
    return algorithm;
  }

  public String getAlgorithmTag() {
    return algorithmTag;
  }

  public String getProblemTag() {
    return problemTag;
  }

  public String getReferenceParetoFront() { return referenceParetoFront; }

  public int getRunId() { return this.runId;}
}
