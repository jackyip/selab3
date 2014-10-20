import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class jiedui {
	public static char[] ss = new char[1000];
	public static int b = 0;
	public static double reqTR = 0, reqTP = 0;
	public static int[] I = new int[16];
	public static int num[] = new int[16];
	public static double TP[] = new double[16];
	public static double TR[] = new double[16];

	public static void main(String[] args) {
		process();
		req();
		test("SERVICE.txt");
		shuchu();
	}

	public static void process() {
		int visit[] = new int[16];
		System.out.println("请输入服务流程DAG：");
		Scanner sca = new Scanner(System.in);
		String s = sca.next();
		ss = s.toCharArray();
		for (int k = 0; k < 14; k++) {
			visit[k] = 0;
		}
		for (int i = 0; i < s.length(); i++) {
			if (ss[i] >= 'A' && ss[i] <= 'Z' && visit[ss[i] - 65] == 0) {
				visit[ss[i] - 65] = 1;
				num[b] = ss[i] - 64;
				b++;

			}
		}
	}

	public static void req() {
		System.out.println("请输入客户需求:");
		Scanner sca = new Scanner(System.in);
		String s = sca.next();
		char ss[] = s.toCharArray();
		int sf[] = new int[ss.length];
		int i = 3;
		int roll = 1;
		while (ss[i] != ',') {
			sf[i] = ss[i] - 48;
			reqTR += sf[i] * (Math.pow(10, (-roll)));
			i++;
			roll++;
		}
		int j = sf.length - 2, rol = 0;
		while (ss[j] != ',') {
			sf[j] = ss[j] - 48;
			reqTP += sf[j] * Math.pow(10, (rol));
			j--;
			rol++;
		}
	}

	public static void test(String filePath) {
		BufferedReader br = null;
		int index = 1, flag, flag2;
		double reqTPsingle, reqTRsingle;
		String line;
		double[] sf = new double[5];
		double[] temp = new double[b * 500];
		double[] TRtemp = new double[b * 500];
		double[] TPtemp = new double[b * 500];
		int I1[] = new int[b * 500];
		double[] mark = new double[16];
		int mm = 0;
		try {
			br = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		try {
			while (index <= num[b - 1]) {
				if (index == num[mm]) {
					reqTRsingle = Math.pow(reqTR, (1 / (double) b));
					reqTPsingle = reqTP / b;
					flag = 0;
					flag2 = 0;
					while ((flag2 < 500) && (line = br.readLine()) != null) {
						String[] se = line.split(" ");
						sf[2] = Double.parseDouble((String) se[2]);
						sf[4] = Double.parseDouble((String) se[4]);
						if ((sf[2] >= reqTRsingle) && (sf[4] <= reqTPsingle)) {
							temp[flag] = (double) (sf[2] / sf[4]);
							TRtemp[flag] = sf[2];
							TPtemp[flag] = sf[4];
							I1[flag] = flag2;
							flag++;
						}

						flag2++;
					}
					mark[index] = temp[0];
					for (int i = 0; i < flag; i++) {
						if (temp[i] > mark[index]) {
							mark[index] = temp[i];
							I[index] = I1[i] + 1;
							TR[index] = TRtemp[i];
							TP[index] = TPtemp[i];
						} else {
							I[index] = I1[i] + 1;
							TR[index] = TRtemp[i];
							TP[index] = TPtemp[i];

						}
					}
					mm++;
				} else {
					int flag3 = 0;
					while ((flag3 < 500) && (line = br.readLine()) != null) {
						flag3++;
					}
				}
				index++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shuchu() {
		double rel = 1, pri = 0, Q = 0;
		int j = 1, i = 0;
		for (int k = 0; k < ss.length; k++) {
			if (ss[k] >= 'A' && ss[k] <= 'Z')
				System.out.print(ss[k] + "-" + (I[ss[k] - 64]));
			else
				System.out.print(ss[k]);
		}
		while (i < b) {
			if (j == num[i]) {
				rel *= TR[j];
				pri += TP[j];
				i++;
			}
			j++;
		}
		Q = rel - pri / 100;
		System.out.println(",Reliability=" + rel + "," + "Cost=" + pri + ","
				+ "Q=" + Q + ".");

	}
}
