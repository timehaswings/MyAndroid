package com.weylen.xmlparsedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    // 节点：元素节点 文本节点(空白部分也是属于文本节点) 注释节点
    // DOM SAX PULL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullParse();
    }

    /**
     * DOM解析
     * 优点：解析快，想要得到指定的元素的内容比较简单。
     * 缺点：耗内存 在解析大文件内存消耗比较大
     */
    public void domParse(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Document对象里面包含了要解析的xml文件的所有的数据
            Document document = builder.parse(getAssets().open("xml/test.xml"));
//            document.getElementsByTagName("test");
            // 获取文档的根标签的元素对象
            Element element = document.getDocumentElement();
            String tagName = element.getTagName(); // 得到元素的名字
            Log.d("zhou", "domParse: tagName:"+tagName);
            NodeList list = element.getChildNodes(); // 得到标签的所有的子节点列表
            Log.d("zhou", "domParse: 子节点列表的size："+list.getLength());
            // 遍历节点
            for (int i = 0; i < list.getLength(); i++){
                Node node = list.item(i); // 得到指定下标对应的节点对象
                short type = node.getNodeType(); // 得到节点类型
                if (type == Node.ELEMENT_NODE){
                    // 向下转型Element对象
                    Element nodeElement = (Element) node;
                    String attr = nodeElement.getAttribute("name");
                    Log.d("zhou", "domParse: attr:"+attr);
                    NodeList childList = node.getChildNodes();
                    for (int j = 0; j < childList.getLength(); j++){
                        Node childNode = childList.item(j);
                        short childType = childNode.getNodeType();
                        if (childType == Node.COMMENT_NODE){// 如果是注释节点 则输出注释的内容
                            String comment = childNode.getNodeValue(); // 得到节点值
                            Log.d("zhou", "domParse: comment:"+comment);
                        }else if (childType == Node.ELEMENT_NODE){// 如果是元素节点 则取得节点的值
                            String nodeName = childNode.getNodeName(); // 节点名
                            String nodeValue = childNode.getTextContent(); // 取得节点内的文本内容
                            Log.d("zhou", "domParse: nodeName:"+nodeName +",nodeValue:"+nodeValue);
                        }
                    }
                }

                Log.d("zhou", "domParse: ------------------------------------");
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SAX 解析
     * 优点：不占内存
     * 缺点：速度慢 想要得到其中某一个标签的内容的时候非常麻烦
     */
    public void saxParse(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            // parse方法是一个阻塞方法 必须等待解析文件结束之后
            parser.parse(getAssets().open("xml/test.xml"), new MyHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MyHandler extends DefaultHandler2{
        private String tag = null;
        @Override // 读取到文档的开始
        public void startDocument() throws SAXException {
            Log.d("zhou", "startDocument: 文档开始");
        }

        @Override // 元素开始 1. 命名空间地址 2. 不带命名空间的别名的名字test  3.带别名的名字tools:test 4. 属性集合
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("test".equals(localName)){
                // 取出属性值
                String attr = attributes.getValue(0);// 第一个属性值
                Log.d("zhou", "startElement: test-->attr:"+attr);
            }else if ("tag".equals(localName) || "flag".equals(localName)){
                tag = localName;
            }

        }

        @Override // 元素结束
        public void endElement(String uri, String localName, String qName) throws SAXException {
            tag = null;
        }

        @Override // 文档结束
        public void endDocument() throws SAXException {
            Log.d("zhou", "endDocument: 文档结束");
        }

        @Override // 字符内容
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (tag != null){
                Log.d("zhou", "characters: name："+tag+",value:"+value);
            }
            super.characters(ch, start, length);
        }

        @Override
        public void comment(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            Log.d("zhou", "comment: "+value);
        }
    }

    /**
     * PULL解析
     * ?? 如何将一个字符串转换成一个字符流StringReader
     *
     */
    public void pullParse(){
        try {
            String source = "<tag>test</tag><flag>这是一个字符串</flag>";
            StringReader stringReader = new StringReader(source);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(getAssets().open("xml/test.xml"), "utf-8");
            parser.setInput(stringReader);

            int eventType = parser.next(); // 取得下一次的事件值
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    // 文档开始
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    // 元素开始
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();// 取得元素名字
                        if ("test".equals(name)){
                            // 取得属性值
                            // 1.命名空间 2.属性名字
                            String attr = parser.getAttributeValue(null, "name");
                            Log.d("zhou", "pullParse: attr:"+attr);
//                            parser.getAttributeValue(0);
                        }else if ("tag".equals(name) || "flag".equals(name)){
                            String text = parser.nextText();
                            Log.d("zhou", "pullParse: name;"+name +",value:"+text);
                        }
                        break;
                    // 文本
                    case XmlPullParser.TEXT:
                        break;
                    // 元素结束
                    case XmlPullParser.END_TAG:

                        break;
                    // 文档结束
                    case XmlPullParser.END_DOCUMENT:

                        break;
                    // 注释
                    case XmlPullParser.COMMENT:
                        Log.d("zhou", "pullParse: comment:"+parser.getText());
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
