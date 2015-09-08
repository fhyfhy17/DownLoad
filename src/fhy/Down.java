package fhy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Down {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("111.txt"));
		String content = "";
		while ((content = bf.readLine()) != null) {
			URL url = new URL(content);
			// 打开链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为8秒
			conn.setConnectTimeout(8 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			System.out.println(content);
			int d = 0;
			if (content.contains(".com") || content.contains(".cn")) {
				int b = content.indexOf(".com");
				int c = content.indexOf(".cn");

				if (b != -1) {
					d = b + 4;
				} else if (c != -1) {
					d = c + 3;
				} else {
					System.out.println("字符有误");
					return;
				}
			}
			// String c = content.substring(20);
			String c = content.substring(d);

			c.replaceAll("\\\\", "/");
			System.out.println(c);

			File f = new File(c);
			createFile(f);
			FileOutputStream fs = new FileOutputStream(f);
			BufferedOutputStream bo = new BufferedOutputStream(fs);
			byte[] buffer = new byte[1204];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				bo.write(buffer, 0, len);
			}

			bo.flush();
			inStream.close();
			System.out.println("创建文件完毕");
		}

	}

	public static boolean createFile(File file) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		return file.createNewFile();
	}
}
