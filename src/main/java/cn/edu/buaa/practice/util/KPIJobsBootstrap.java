/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.buaa.practice.util;

import org.apache.hadoop.util.ProgramDriver;

import cn.edu.buaa.practice.job.BrowserCountJob;
import cn.edu.buaa.practice.job.HourPvCountJob;
import cn.edu.buaa.practice.job.IPCountJob;
import cn.edu.buaa.practice.job.PVCountJob;
import cn.edu.buaa.practice.job.SourceCountJob;

/**
 * A description of an example program based on its class and a 
 * human-readable description.
 */
public class KPIJobsBootstrap {
  
  public static void main(String argv[]){
    int exitCode = -1;
    ProgramDriver pgd = new ProgramDriver();
    try {
      pgd.addClass("pvcount", PVCountJob.class, "MR job to count pv.");
      pgd.addClass("uvcount", IPCountJob.class, "MR job to count uv.");
      pgd.addClass("timecount", HourPvCountJob.class, "MR job to count by datetime/hour.");
      pgd.addClass("browsercount", BrowserCountJob.class, "MR job to count browser.");
      pgd.addClass("sourcecount", SourceCountJob.class, "MR job to count by source/referer.");
      exitCode = pgd.run(argv);
    }
    catch(Throwable e){
      e.printStackTrace();
    }
    
    System.exit(exitCode);
  }
}
	
