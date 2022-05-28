package httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFilesFromURL {
    String BaseURL_String = null;
    String encoding_String = null;
    String tmpFilename = null;

    //构造函数，默认是香港公有云的HFS
    public DownloadFilesFromURL() {
        String tmpFilename = "tmp.html";
        //请求URL连接
        BaseURL_String = "https://naas-intl.huaweicloud.com:11125/";
        //加密用户名&密码
        try {
            encoding_String = DatatypeConverter.printBase64Binary("wyj:Huawei@123".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //构造函数，其它下载网址
    public DownloadFilesFromURL(String url, String usernamePassword) {
        //请求URL连接
        BaseURL_String = url;
        //加密用户名&密码
        try {
            encoding_String = DatatypeConverter.printBase64Binary("wyj:Huawei@123".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载某网页并生成html文件
     *
     * @param filename
     * @param url
     * @return
     */
    public String downloadFile(String url, String filename) {
        try {
            URL Base_URL = new URL(url);
            HttpURLConnection connection_HttpURLConnection = (HttpURLConnection) Base_URL.openConnection();

            //设置请求参数
            connection_HttpURLConnection.setRequestMethod("GET");
            connection_HttpURLConnection.setDoOutput(true);
            connection_HttpURLConnection.setRequestProperty("Authorization", "Basic " + encoding_String);
            //请求URL并写入文件
            InputStream content_InputStream = connection_HttpURLConnection.getInputStream();
            BufferedReader in_BufferedReader = new BufferedReader(new InputStreamReader(content_InputStream));
//            FileUtils.writeStringToFile(new File(".\\" + filename), IOUtils.toString(in_BufferedReader), "UTF-8");

            //将读取的文件写到本地
            FileOutputStream out_FileOutputStream = new FileOutputStream(".\\" + filename);
            byte[] b = new byte[1024];
            int count = -1;
            while ((count = content_InputStream.read(b)) >= 0) {
                out_FileOutputStream.write(b, 0, count);
            }

            //关闭输入、输出流
            out_FileOutputStream.flush();
            out_FileOutputStream.close();
            content_InputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return filename;
    }

    /**
     * 批量下载文件
     * @param filename
     */
    public void downloadFiles(String filename) {
        try {
            //读取文件并即将进行解析
            File tmpFile = new File(".\\" + filename);
            Document doc = Jsoup.parse(tmpFile, "UTF-8", "");

            //读取下载链接并循环下载
            Elements links = doc.select("a");
            for (int i = 1; i < links.size(); i++) {
                String URLStr = null;
                URLStr = BaseURL_String + links.get(i).attr("href");
                String FileName = links.get(i).text();
                downloadFile(URLStr, FileName);
                //删除文件
                tmpFile.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DownloadFilesFromURL downloadFilesFromURL = new DownloadFilesFromURL();
        downloadFilesFromURL.downloadFile(downloadFilesFromURL.BaseURL_String,downloadFilesFromURL.tmpFilename);
        downloadFilesFromURL.downloadFiles(downloadFilesFromURL.tmpFilename);
    }
}