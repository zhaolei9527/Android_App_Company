package easeui.domain;

public class EaseEmojicon {
    public EaseEmojicon(){
    }
    
    /**
     * constructor
     * @param icon- resource id of the ic_launcher
     * @param emojiText- text of emoji ic_launcher
     */
    public EaseEmojicon(int icon, String emojiText){
        this.icon = icon;
        this.emojiText = emojiText;
        this.type = Type.NORMAL;
    }
    
    /**
     * constructor
     * @param icon - resource id of the ic_launcher
     * @param emojiText - text of emoji ic_launcher
     * @param type - normal or big
     */
    public EaseEmojicon(int icon, String emojiText, Type type){
        this.icon = icon;
        this.emojiText = emojiText;
        this.type = type;
    }
    
    
    /**
     * identity code
     */
    private String identityCode;
    
    /**
     * static ic_launcher resource id
     */
    private int icon;
    
    /**
     * dynamic ic_launcher resource id
     */
    private int bigIcon;
    
    /**
     * text of emoji, could be null for big ic_launcher
     */
    private String emojiText;
    
    /**
     * name of emoji ic_launcher
     */
    private String name;
    
    /**
     * normal or big
     */
    private Type type;
    
    /**
     * path of ic_launcher
     */
    private String iconPath;
    
    /**
     * path of big ic_launcher
     */
    private String bigIconPath;
    
    
    /**
     * get the resource id of the ic_launcher
     * @return
     */
    public int getIcon() {
        return icon;
    }


    /**
     * set the resource id of the ic_launcher
     * @param icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }


    /**
     * get the resource id of the big ic_launcher
     * @return
     */
    public int getBigIcon() {
        return bigIcon;
    }


    /**
     * set the resource id of the big ic_launcher
     * @return
     */
    public void setBigIcon(int dynamicIcon) {
        this.bigIcon = dynamicIcon;
    }


    /**
     * get text of emoji ic_launcher
     * @return
     */
    public String getEmojiText() {
        return emojiText;
    }


    /**
     * set text of emoji ic_launcher
     * @param emojiText
     */
    public void setEmojiText(String emojiText) {
        this.emojiText = emojiText;
    }

    /**
     * get name of emoji ic_launcher
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * set name of emoji ic_launcher
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get type
     * @return
     */
    public Type getType() {
        return type;
    }


    /**
     * set type
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }


    /**
     * get ic_launcher path
     * @return
     */
    public String getIconPath() {
        return iconPath;
    }


    /**
     * set ic_launcher path
     * @param iconPath
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }


    /**
     * get path of big ic_launcher
     * @return
     */
    public String getBigIconPath() {
        return bigIconPath;
    }


    /**
     * set path of big ic_launcher
     * @param bigIconPath
     */
    public void setBigIconPath(String bigIconPath) {
        this.bigIconPath = bigIconPath;
    }

    /**
     * get identity code
     * @return
     */
    public String getIdentityCode() {
        return identityCode;
    }
    
    /**
     * set identity code
     * @param identityId
     */
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public static String newEmojiText(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }



    public enum Type{
        /**
         * normal ic_launcher, can be input one or more in edit view
         */
        NORMAL,
        /**
         * big ic_launcher, send out directly when your press it
         */
        BIG_EXPRESSION
    }
}
