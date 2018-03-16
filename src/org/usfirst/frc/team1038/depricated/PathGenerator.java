package org.usfirst.frc.team1038.depricated;

@Deprecated
public class PathGenerator {
	public static void generate()
	{
		String[] autons = { /*"None", "LToLSwitch", "RToRSwitch",*/"CToLSwitch", "CToRSwitch" , /*"LSwitchToLCube", "RSwitchToRCube",
				"LSwitchtoRCube", "RSwitchToLCube",  "LCubeToLScale", "LCubeToRScale", "RCubeToRScale", "RCubeToLScale",
				"RSwitchToRScale", "LSwitchToLScale"*/};
		
		AutonWaypointPath pathWriter = AutonWaypointPath.getInstance();
		for (String auton : autons)
		{
//		String auton = "CToRSwitch";
//			System.out.println("Writing Auton: " + auton);
//			pathWriter.writeToFile(pathWriter.getWaypointPath(auton), auton);
//			System.out.println("Wrote Auton: " + auton);
		}
		System.out.println("Paths generated");
	}
}
