//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.runner.multiobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

import java.util.List;

/**
 * Class for configuring and running the DMOPSO algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class DMOPSORunner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.runner.multiobjective.DMOPSORunner problemName [referenceFront]
   */
  public static void main(String[] args) throws Exception {
    DoubleProblem problem;
    Algorithm<List<DoubleSolution>> algorithm;

    String referenceParetoFront = "" ;

    String problemName ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
    }

    problem = (DoubleProblem) ProblemUtils.<DoubleSolution> loadProblem(problemName);

    algorithm = new DMOPSO(problem, 100, 250, 0.0, 0.1, 0.0, 1.0, 1.5, 2.5, 1.5, 2.5, 0.1, 0.4, -1.0, -1.0,
            "_TCHE", "", 2) ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute();

    List<DoubleSolution> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }
}
