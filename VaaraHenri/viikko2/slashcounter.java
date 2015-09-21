public class slashcounter {

        public static void main(String args[]) {

                String pwd = args[0];
                int length = pwd.length();
		int count = 0;
//              System.out.println(length);

                while (length > 0)
                {
                        if (pwd.charAt(length-1) == 47) 
                        {
//                              System.out.println("slash found");
				count += 1;
                        }
                        //System.out.println(pwd.charAt(length-1));
                        length -= 1;
                }
		System.out.println(count);
        }

}

