package bucket.util;

import java.util.Map;

/**
 * @Author: qyl
 * @Description: template like: hello,#name#,?#age#,?.
 * @Date: Created in 21:11 2018/6/25
 */
public abstract class StringTempletHelper {

    public static String parse(String template,Map<String,String> param){
        Parser parser = new Parser(template,param);
        return parser.parse();
    }

    private static class Parser{
        private int index;
        private Map<String,String> param;
        private String template;

        public Parser(String template,Map<String,String> param){
            this.index = 0;
            this.param = param;
            this.template = template;
        }

        public String parse(){
            StringBuilder result = new StringBuilder(50);
            char[] src = template.toCharArray();

            for(;;){
                if (src[index] == '#'){
                    index++;
                    String key = readKey(src);
                    result.append(param.get(key));
                }else if (src[index] == '?'){
                    index++;
                    result.append(readNullable(src));
                }else {
                    result.append(src[index]);
                }
                index++;
                if (index >= src.length){
                    break;
                }
            }
            return result.toString();
        }

        private String readKey(char[] src){
            char[] dest = new char[16];
            int destIndex = 0;
            for(; index < src.length; ){
                if ('#' == src[index]) break;
                dest[destIndex] = src[index];
                destIndex++;
                index++;
            }
            return new String(dest).trim();
        }

        private String readNullable(char[] src){
            char[] dest = new char[32];
            int destIndex = 0;
            boolean need = false;
            for(; index < src.length; ){
                if ('?' == src[index]){
                    break;
                }else if ('#' == src[index]){
                    index++;
                    String key = readKey(src);
                    String value = param.get(key);
                    if(value != null){
                        need = true;
                        System.arraycopy(value.toCharArray(),0,dest,destIndex,value.length());
                        destIndex = destIndex + value.length() - 1;
                    }
                }else {
                    dest[destIndex] = src[index];
                }
                destIndex++;
                index++;
            }
            return need ? new String(dest).trim() : "";
        }
    }
}
