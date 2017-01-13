import java.io.*;
import java.util.*;

interface Constants
{
	int N=10000;
	int M=1000;
	String path="/home/vinay/Desktop/python/dJango/pocketTanks/bots/";
}

class OutputFile implements Constants
{
   File file;
   FileWriter fw;
   BufferedWriter bw;
   OutputFile(String str) throws IOException
   {
     str=path+"score/"+str; 											//add folder in which output file are to be stored
    	 File file = new File(str);
     if (!file.exists())
     {
	  	 file.createNewFile();
     }
	     fw = new FileWriter(file.getAbsoluteFile());
    	 bw = new BufferedWriter(fw);
   }
   public void output(String str) throws IOException
   {
   		bw.write(str);
   		bw.flush();
   }
   public void finish()
   {
   		try
   		{
   			bw.close();
   		}
   		catch(Exception e)
   		{
   			//e.printStackTrace();
   		}
   	}
 }


class Animation implements Constants
{
   File file;
   FileWriter fw;
   BufferedWriter bw;
   Animation(String str)throws IOException
   {
     str=path+"animation/"+str;								//add folder in which output file are to be stored
     File file = new File(str);
     if (!file.exists())
     {
	file.createNewFile();
     }
     fw = new FileWriter(file.getAbsoluteFile());
     bw = new BufferedWriter(fw);
   }
	public void outFirstLine(String s1,String s2) throws IOException
	{
		bw.write(s1+" "+s2);
		bw.newLine();
		bw.flush();
	}
   public void output(Tank_j x,Tank_j y,boolean turn,int x_hit,String type) throws IOException//make sure this runs for a particular Tank_j only after update score has been invoked
   {
		int p=0;
	if(type.compareTo("xFalcon")==0)
		p=1;
	else if(type.compareTo("xSeaEagle")==0)
		p=2;
	else if(type.compareTo("xAgni-V")==0)
		p=3;

     if(turn)
     {
     	/*System.out.print("1 "+x.current_position+" "+x.score+" "+x_hit+" "+type+" "+x.next_aim+" 0");
      System.out.println("0 "+y.current_position+" "+y.score+" -1 -1 -1 0");  */

       bw.write("1 "+x.current_position+" "+x.score+" "+x_hit+" "+p+" "+x.next_aim+" 0 ");
       bw.write("0 "+y.current_position+" "+y.score+" -1 -1 -1 0");
	   bw.newLine();
     }
     else
     {
     /*	System.out.print("0 "+x.current_position+" "+x.score+" -1 -1 -1 0");
       System.out.println("1 "+y.current_position+" "+y.score+" "+x_hit+" "+type+" "+y.next_aim+" 0");  */


       bw.write("0 "+x.current_position+" "+x.score+" -1 -1 -1 0 ");
       bw.write("1 "+y.current_position+" "+y.score+" "+x_hit+" "+p+" "+y.next_aim+" 0");
	   bw.newLine();
     }
     bw.flush();
   }
	public void finish()
	{
		try
		{
			bw.close();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
   /*public static void main(String args[]) throws IOException
   {
	   Tank_j x=new Tank_j(50,40);
	   Tank_j y=new Tank_j(50,40);
	   x.next_aim=-1;
	   y.next_aim=0;
	   Animation a=new Animation("sample.txt");
	   a.output(x,y,true,30);
	   a.output(x,y,false,10);
	   a.bw.close();
   } */
}


class Tank_j
{
  int n,current_position,score,no_of_moves,pow_count,maxsea,agni,sea,max_pow,xfalcon,next_aim;
  public Tank_j(int n,int m)                             //Initial position as per value of n and m
  {
    current_position=n;
    this.n=n;
    score=no_of_moves=pow_count=sea=agni=xfalcon=next_aim=0;
    next_aim=2;
    maxsea=10;
    max_pow=5;
  }
  public int update_position(int pos)                // Update Position as per pos
  {
    current_position=pos;
    return pos;
  }
  public boolean is_directly_hit(int x_hit,int x_target)//give indication to other as true or false for position change as per direct hit or not
  {
       if(x_hit==x_target)
         return true;
       else
         return false;
  }
  public boolean isSeaAvail()                    // Is sea weapon available
  {
      if(sea>=maxsea)
        return false;
      else
        return true;
  }
  public boolean isAgniAvail()                 // Is agni available
  {
      if(agni>=1)
        return false;
      else
        return true;
  }
  public boolean is_spec_pow_avail()          // Is spec_power weapon(for position change) available
  {
     if(pow_count>=max_pow)
       return false;
     else
       return true;
  }
  public int update_score(String type, int x_hit, int x_target)   //Update score as per weapon type, hit position and target position
  {
     no_of_moves++;
     if(type.compareTo("xAgni-V")==0)
     {
         update_weapons_count("xAgni-V");
         if(x_hit==x_target)
           {
		score+=500;
  		next_aim=0;
	   }
         else if(x_target>=Math.max(1,x_hit-n/50)&&x_target<=Math.min(n,x_hit+n/50))  // Ambiguity- whether use absolute or not
          {
		next_aim=0;
 		score=score+500/(Math.abs(x_hit-x_target));
	  }
         else if(x_target<Math.max(1,x_hit-n/50))
               next_aim=-1;
         else
               next_aim=1;
     }
     else if(type.compareTo("xSeaEagle")==0)
     {
         update_weapons_count("xSea Eagle");
         if(x_target>=Math.max(1,x_hit-n/100)&&x_target<=Math.min(n,x_hit+n/100))
         {
		score+=50;
		next_aim=0;
	 }
	else if(x_target<Math.max(1,x_hit-n/100))
		next_aim=-1;
        else
       		next_aim=1;
     }
     else if(type.compareTo("xFalcon")==0)
     {
	 update_weapons_count("xFalcon");
         if(x_hit==x_target)
         {
		score+=100;
		next_aim=0;
	 }
         else if(x_hit>x_target)
               next_aim=-1;
         else
               next_aim=1;
     }
     return score;
  }
  public int display_no_of_moves()       // Displaying no. of moves moved
  {
       return no_of_moves;
  }
  public void update_weapons_count(String s)            //Updating count of weapon specified in argument
  {
     if(s.compareTo("xAgni-V")==0)
       agni++;
     else if(s.compareTo("xSea Eagle")==0)
         sea++;
     else if(s.compareTo("xFalcon")==0)
         xfalcon++;
  }
  public int display(String s)                      //Displaying weapon count as per String argument
  {
     int i=0;
     if(s.compareTo("Agni-V")==0)
       i=agni;
     else if(s.compareTo("xSea Eagle")==0)
       i=sea;
     else if(s.compareTo("xFalcon")==0)
        i=xfalcon;
     return i;
  }
  public boolean is_valid_special_pos(int new_pos)          //checking for new position for using special power
  {
    if(new_pos>=Math.max(1,current_position-n/100) && new_pos<=Math.min(n,current_position+n/100))
        return true;
    else
       return false;
  }
  public int update_special_pos(int x)                //Update position specified by special power in argument
  {
    pow_count++;
    return(update_position(x));
  }
  public int find_next_aim()                  //1,-1,0 depending upon target range
   {
          return next_aim;
   }
}

class Checker implements Constants{
	static int linelen=N;						//Value of N
	Checker(int n)
	{
		linelen=n;
	}
	public static boolean isInitialPositionValid(String pos)
	{
		int p;
		try{
		p=Integer.parseInt(pos);
		}
		catch(Exception e)
		{
			System.out.println("initial int position error");
			return false;
		}
		if(p<=linelen && p>=1)
		{
			return true;
		}
		System.out.println("initial position error");

		return false;
	}
	public static boolean isLine1Valid(String newpos,boolean flag,Tank_j player)
	{
		int pos;
		try{
		pos=Integer.parseInt(newpos);
		}
		catch(Exception e)
		{
			System.out.println("line1 int pos error");
			return false;
		}
		if(!flag)
		{
			if(pos==0)
			return true;
			else if(player.is_spec_pow_avail()&&pos<=Math.min(linelen,player.current_position+linelen/100)&&pos>=Math.max(1,player.current_position-linelen/100))
			{


				return true;
			}
			else
			{	System.out.println("special power out of limit error");
				return false;
			}
		}
		else
		{
			if(pos<=linelen&&pos>=1)
			{
				return true;
			}
			else
			{
				System.out.println("new pos out of limit error");
				return false;
			}
		}
	}
	public static boolean isLine2Valid(String move, Tank_j player)
	{
		int pos,i,posei=0;
		String missile;
		for(i=0;i<move.length();i++)
		{
			if(move.charAt(i)>=48 &&  move.charAt(i)<=57)
			{
				posei++;
			}
			else
			break;
		}
		try{

			pos=Integer.parseInt(move.substring(0,posei));
			missile=move.substring(posei+1);
		}
		catch(Exception e)
		{
			System.out.println("line 2 int target pos error");
			return false;
		}
		if(pos>=1&&pos<=linelen)
		{
			if(missile.equals("xFalcon"))
				return true;
			else if(missile.equals("xSeaEagle"))
			{
				if(player.isSeaAvail())
				{
					return true;
				}
				else
				{
					System.out.println("Seaegale not avail error");
					return false	;
				}
			}
			else if(missile.equals("xAgni-V"))
			{
				if(player.isAgniAvail())
				{
					return true;
				}
				else
				{
			System.out.println("agni not avail error");
					return false;
				}

			}
			else
			{
				System.out.println("missile string error");
				return false;
			}

		}
		else
		{
	System.out.println("line 2 pos limmit error");
		return false;
		}
	}
	/*public static void main(String args[])
	{
		Tank_j a=new Tank_j(1000,1000);
		Checker check=new Checker(1000);
		System.out.println(check.isLine1Valid("a",1,a));
	}*/
}

class Compile
{
	public static boolean comp(String source,String exe,String lang)
	{
		boolean result=false;
		try
		{
			ProcessBuilder pb = new ProcessBuilder();
			if(lang.compareTo("c")==0)
				pb=new ProcessBuilder("gcc","-o",exe,source);
			else if(lang.compareTo("cpp")==0)
				pb=new ProcessBuilder("g++","-o"+exe,source);
			else if(lang.compareTo("java")==0)
				pb=new ProcessBuilder("javac",source);
			Process p = pb.start();
			int errcode=p.waitFor();
			if(errcode==0)
			{
				result = true;
			}
		}
		catch(Exception e)
		{

		}
		return result;
	}

}

class Bot implements Runnable,Constants
{
	String name;									//name of the executable file
	PipedReader pr;
	PipedWriter pw;
	Tank_j a;
	Tank_j b;
	Process p;
	String lang;
	boolean first;									//true for bot making the first turn
	int error=0;									//non-zero if program is aborted due to run-time error
	boolean inValidMove=false;						//true if the bot has maode an invalid move
	boolean wait=false;
	boolean earlyTerminate=false;
	Animation anime;
	Bot(Tank_j a,Tank_j b,PipedReader pr,PipedWriter pw,String name,boolean flag,String lang,Animation anime)
	{
		this.a=a;
		this.b=b;
		this.pw=pw;
		this.pr=pr;
		this.name=name;
		this.first=flag;
		this.lang=lang;
		this.anime=anime;
	}
	public String convert(String s1,String s2,boolean flag_hit)
	{
		int p=Integer.parseInt(s1);
		if(p!=0)
		{
			if(flag_hit)
			a.update_position(p);
			else
			a.update_special_pos(p);
		}

		String[] s=s2.split("\\s+");
		p=Integer.parseInt(s[0]);
		a.update_score(s[1],p,b.current_position);

		try
		{
			if(first)
				anime.output(a,b,first,p,s[1]);
			else
				anime.output(b,a,first,p,s[1]);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}

		String ret=null;
		if(a.next_aim==0)
		ret="0";
		else if(a.next_aim==-1)
		ret="-";
		else
		ret="+";

		return ret;
	}
	public boolean update_flag_hit(String s)
	{
		int p=Integer.parseInt(s);
		if(p==1)
		return true;
		else
		return false;
	}
	public void run()
	{
		try
		{
			ProcessBuilder pb = new ProcessBuilder();
			if(lang.compareTo("c")==0 || lang.compareTo("cpp")==0)
				pb=new ProcessBuilder("./"+name);
			else
				pb=new ProcessBuilder("java",name );
			p=pb.start();

			OutputStream out=p.getOutputStream();
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(out));

			InputStream in=p.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(in));

			BufferedReader buffer_pr=new BufferedReader(pr);
			BufferedWriter buffer_pw=new BufferedWriter(pw);

			buffer_pw.write("Start"+"\n");
			buffer_pw.flush();
			String stemp=buffer_pr.readLine();

			wait=true;
			String str1=br.readLine();
			wait=false;
			if(str1==null)
			{
				error=p.waitFor();
				br.close();
				bw.close();
				buffer_pr.close();
				buffer_pw.close();
				return;
			}
			str1=str1.trim();
			if(Checker.isInitialPositionValid(str1))
				a.update_position(Integer.parseInt(str1));

			else
			{
				inValidMove=true;
				br.close();
				bw.close();
				buffer_pr.close();
				buffer_pw.close();
				return;
			}

			String out_str1=null;
			String out_str2=null;
			boolean flag_hit=false;
			String str2="";
			for(int i=1;i<=M;i++)
			{

				if(first && i==1)
					str1="0";
				else
					str1=buffer_pr.readLine();


				flag_hit=update_flag_hit(str1);
				try
				{
					bw.write(str1+" "+str2+"\n");
					bw.flush();
				}
				catch(Exception e)
				{
					earlyTerminate=true;
					error=p.waitFor();
					//e.printStackTrace();
				}

				try
				{
					wait=true;
					out_str1=br.readLine();
					out_str2=br.readLine();
					wait=false;
				}
				catch(Exception e)
				{
					earlyTerminate=true;
					error=p.waitFor();
					//e.printStackTrace();
				}
				/*if(first){
				System.out.println(out_str1);
				System.out.println(out_str2);
				} */



				if(out_str1==null || out_str2==null)
				{
					error=p.waitFor();
					break;
				}

				out_str1=out_str1.trim();
				out_str2=out_str2.trim();

				if((!Checker.isLine1Valid(out_str1,flag_hit,a)) || (!Checker.isLine2Valid(out_str2,a)))
				{
					inValidMove=true;
					break;
				}

				str2=convert(out_str1,out_str2,flag_hit);

				if(str2.compareTo("0")==0)
					str1="1";
				else
					str1="0";

				if(i==M && (!first))
				{

					try
					{
						buffer_pw.write(str1+"\n");
						buffer_pw.flush();
					}
					catch(Exception e)
					{
						//e.printStackTrace();
					}
				}
				else
				{
					buffer_pw.write(str1+"\n");
					buffer_pw.flush();
				}
			}
			br.close();
			bw.close();
			buffer_pr.close();
			buffer_pw.close();

		}
		catch(Exception e)
		{
			try
			{
				Thread.sleep(2);
			}
			catch(Exception e1)
			{
				//e1.printStackTrace();
			}
			//e.printStackTrace();
			p.destroy();
		}
	}
}

class judge
{
	public static void main(String arg[]) throws InterruptedException,IOException
	{
		String source_file1=arg[0];								// name of 1st source file
		String source_file2=arg[1];								//name of 2nd source file

		//For compiling the code

		//System.out.println(arg[0]);
		//System.out.println(arg[1]);
		int dotPos=0;
		for(int i=0;i<source_file1.length();i++)
		{
			if(source_file1.charAt(i)=='.')
			{
				dotPos=i;break;
			}
		}

		String exe1=source_file1.substring(0,dotPos);
		String lang1=source_file1.substring(1+dotPos);
		for(int i=0;i<source_file2.length();i++)
		{
			if(source_file2.charAt(i)=='.')
			{
				dotPos=i;break;
			}
		}

		String exe2=source_file2.substring(0,dotPos);
		String lang2=source_file2.substring(1+dotPos);

//		System.out.println(exe1+' '+lang1+' '+exe2+' '+lang2);



		//For compiling the code

		Compile pr1 = new Compile();
		Compile pr2 = new Compile();
		boolean status1 = pr1.comp(source_file1,exe1,lang1);
		boolean status2 = pr2.comp(source_file2,exe2,lang2);

//		System.out.println(status1);
//		System.out.println(status2);


		String filename=exe1+"_"+exe2+".txt";
		Animation anime=new Animation(filename);
		OutputFile f=new OutputFile(filename);

		anime.outFirstLine(exe1,exe2);
		f.output(exe1);
		f.output(" ");
		f.output(exe2);
		f.output("\n");


		if(status1 && status2)
		{
			Tank_j x=new Tank_j(100000,1000);
			Tank_j y=new Tank_j(100000,1000);

			PipedReader pr_b1=new PipedReader();
			PipedWriter pw_b1=new PipedWriter();
			PipedReader pr_b2=new PipedReader();
			PipedWriter pw_b2=new PipedWriter();
			pr_b1.connect(pw_b2);
			pr_b2.connect(pw_b1);


			Bot b1=new Bot(x,y,pr_b1,pw_b1,exe1,true,lang1,anime);
			Bot b2=new Bot(y,x,pr_b2,pw_b2,exe2,false,lang2,anime);

			Thread t1=new Thread(b1);
			Thread t2=new Thread(b2);

			t1.start();
			t2.start();

		/*	t1.join();
			t2.join();  */

			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}


			b1.p.destroy();
			b2.p.destroy();


			/*if(b1.error!=0)
				System.out.println("run time error in 1st program");
			else if(b2.error!=0)
				System.out.println("run time error in 2nd program");

			else if(b1.inValidMove)
				System.out.println("Invalid Move By First Bot");
			else if(b2.inValidMove)
				System.out.println("Invalid Move By Second Bot");


			else if(b1.wait)
				System.out.println("Deadlock due to First Bot");
			else if(b2.wait)
				System.out.println("Deadlock due to Second Bot");

			else
			{
				System.out.println("Bot1 Score="+x.score);
				System.out.println("Bot2 Score="+y.score);
			} */

			if(b1.wait || b2.wait)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Time Limit Exceeded");
			}
			else if(b1.error!=0)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Run Time Error for " + exe1);
			}
			else if(b2.error!=0)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Run Time Error for " + exe2);
			}
			else if(b1.earlyTerminate==true)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Wrong I/O for " + exe1);
			}
			else if(b2.earlyTerminate==true)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Wrong I/O for " + exe2);
			}
			else if(b1.inValidMove)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Invalid Move for " + exe1);
			}
			else if(b2.inValidMove)
			{
				f.output(Integer.toString(0));
				f.output("\n");
				f.output(Integer.toString(0));
				f.output("\n");
				f.output("Invalid Move for " + exe2);
			}
			else
			{
				f.output(Integer.toString(x.score));
				f.output("\n");
				f.output(Integer.toString(y.score));
				f.output("\n");
				if(x.score>y.score)
					f.output(exe1 + " Wins");
				else if(x.score==y.score)
					f.output("Draw");
				else
					f.output(exe2  + " Wins");
				f.output("\n");
			}

		}
		else if((!status1) && (!status2))
		{
			f.output(Integer.toString(0));
			f.output("\n");
			f.output(Integer.toString(0));
			f.output("\n");
			f.output("Compile Time Error for " + exe1+ "and" +exe2);
		}
		else if(!status1)
		{
			f.output(Integer.toString(0));
			f.output("\n");
			f.output(Integer.toString(0));
			f.output("\n");
			f.output("Compile time Error for " + exe1);
		}
		else
		{
			f.output(Integer.toString(0));
			f.output("\n");
			f.output(Integer.toString(0));
			f.output("\n");
			f.output("Compile time Error for " + exe2);
		}

		f.finish();
		anime.finish();
	}
}
