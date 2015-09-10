public class taildirectoryparser {

	public static void main(String args[]) {

		String pwd = args[0];
		int length = pwd.length();

//		System.out.println(length);
	
		while (length > 0)
		{
			if (pwd.charAt(length-1) == 47) 
			{
//				System.out.println("slash found");
				System.out.println(pwd.substring(0,length-1));
				break;
			}
			//System.out.println(pwd.charAt(length-1));
			length -= 1;
		}
	}

}

