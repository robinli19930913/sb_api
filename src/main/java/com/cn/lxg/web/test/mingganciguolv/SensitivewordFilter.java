package com.cn.lxg.web.test.mingganciguolv;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 敏感词过滤
 * @Author : lixuegen
 * @Date ： 2018年12月25日 下午4:17:15
 * @version 1.0
 */
public class SensitiveWordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	//最小匹配规则
	public static int minMatchTYpe = 1;
	//最大匹配规则
	public static int maxMatchType = 2;

	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitiveWordFilter(){
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}

	/**
	 * 判断文字是否包含敏感字符
	 * @author lixuegen
	 * @date 2018年12月25日 下午4:28:30
	 * @param txt  文字
	 * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0,length = txt.length(); i < length; i++){
			//判断是否包含敏感字符
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType);
			//大于0存在，返回true
			if(matchFlag > 0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * @author lixuegen
	 * @date 2018年12月25日 下午5:10:52
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		for(int i = 0 , length = txt.length(); i < length ; i++){
			//判断是否包含敏感字符
			int index = CheckSensitiveWord(txt, i, matchType);
			//存在,加入list中
			if(index > 0){
				sensitiveWordList.add(txt.substring(i, i+index));
				//减1的原因，是因为for会自增
				i = i + index - 1;
			}
		}
		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * @author lixuegen
	 * @date 2018年12月25日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * @author lixuegen
	 * @date 2018年12月25日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * @author lixuegen
	 * @date 2018年12月25日 下午4:31:03
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		//敏感词结束标识位：用于敏感词只有1位的情况
		boolean  flag = false;
		//匹配标识数默认为0
		int matchFlag = 0;
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex , length = txt.length(); i < length ; i++){
			word = txt.charAt(i);
			//获取指定key
			nowMap = (Map) nowMap.get(word);
			//存在，则判断是否为最后一个
			if(nowMap != null){
				//找到相应key，匹配标识+1
				matchFlag++;
				//如果为最后一个匹配规则,结束循环，返回匹配标识数
				if("1".equals(nowMap.get("isEnd"))){
					//结束标志位为true
					flag = true;
					//最小规则，直接返回,最大规则还需继续查找
					if(SensitiveWordFilter.minMatchTYpe == matchType){
						break;
					}
				}
			}
			//不存在，直接返回
			else{
				break;
			}
		}
		//长度必须大于等于1，为词
		if(matchFlag < 1 || !flag){
			matchFlag = 0;
		}
		return matchFlag;
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		SensitiveWordFilter filter = new SensitiveWordFilter();
		String string = "妹妹最太多的伤感苏绣文情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
				+ "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
				+ "难过就躺在某一个人的怀里尽情的阐述心扉苏绣文或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。✨乐华七子NEXT二巡✨✨应援头灯&amp;灯牌贩售公告✨大家之前也看到了我们的灯牌出现在了很多七子在的场合，也熠熠发光地告诉他们告诉所有人团饭的支持一直都在。为了让二巡包括更多的场合中，我们有更多的机会出现在他们面前‼️中首特开头灯&amp;灯牌贩售链接‼️希望有能力的小姐妹们可以认购（还单开了1元链接用于可租赁灯牌的支持）⭐️所有头灯&amp;灯牌按双排灯定制⭐️颜色均为最亮最显眼的白色（❤为红色）⭐️所有价格均按店铺原价包邮定制，⭐️制作结束后店家统一邮寄给大家►若量大从优，则所有余额投入制作同款灯牌存放中首仓库用于平时活动的租赁。" +
				"SPECIAL ROSES FOR ROSÉROSÉCHINABAR圣诞特别活动 玫瑰多刺却香味馥郁，感谢彩英赋予这世界最能代表爱情的花朵更深层的意义，每当偶遇一朵玫瑰，便仿佛与她相见一次。圣诞节被视为万物复苏的开始，崭新一年的希望，在18年最后一月，为了迎接与大家一起的陪伴彩英的新一年，肉吧推出“SPECIAL ROSES FOR ROSÉ”给ROSÉ的情书圣诞特别献礼活动～ 活动时间：2018/12/23 20:00pm-2019/1/1 20:00pm 活动内容：凡于活动时间内满一定金额者将获得肉吧的18FW圣诞特别献礼。礼包分为“荆棘玫瑰”、“月夜野玫瑰”、“樱花与小玫瑰”、“致一朵玫瑰”、“给玫瑰的情书”5个部分。满211 RMB得:“荆棘玫瑰”满422 RMB得：“月夜野玫瑰”满520 RMB得：“樱花与小玫瑰”TOP10得:“致一朵玫瑰”TOP3得:“给玫瑰的情书” 礼包内容：“荆棘玫瑰”：机票/手幅/小海报三选一（待定）+玫瑰胶带/Q版Rosie人物胶带二选一（待定）+玫瑰手指灯+玫瑰胸针 “月夜野玫瑰”：机票/手幅/小海报三选一（待定）+玫瑰胶带/Q版Rosie人物胶带二选一（待定）+玫瑰手指灯+玫瑰胸针+月夜野玫瑰纹身贴+一枝玫瑰花耳坠 “樱花与小玫瑰”：机票/手幅/小海报三选一（随机发）+玫瑰胶带/Q版Rosie人物胶带二选一（待定）+玫瑰手指灯+玫瑰胸针+月夜野玫瑰纹身贴+一枝玫瑰花耳坠+手帐素材玫瑰花贴纸+玫瑰徽章 “致一朵玫瑰”：机票/手幅/小海报三选一（随机发）+玫瑰胶带/Q版Rosie人物胶带二选一（待定）+玫瑰手指灯+玫瑰胸针+月夜野玫瑰纹身贴+一枝玫瑰花耳坠+手帐素材玫瑰花贴纸+玫瑰徽章+肉吧独家Q版ROSÉ卡通人物行李箱贴纸+肉吧独家ROSÉ’greeting台历 “给玫瑰的情书”：机票/手幅/小海报三选一（随机发）+玫瑰胶带/Q版Rosie人物胶带二选一（待定）+玫瑰手指灯+玫瑰胸针+月夜野玫瑰纹身贴+一枝玫瑰花耳坠+手帐素材玫瑰花贴纸+玫瑰徽章+肉吧独家Q版ROSÉ卡通人物行李箱贴纸+肉吧独家ROSÉ new year greeting台历+一枝玫瑰项链+ROSE and ROSIE收纳袋+给玫瑰的情书（将你想对小玫瑰说的话写成情书由肉吧随19年生日礼物一起替你代送至韩国首尔交到彩英手里，大声说出你对彩英的爱吧！）   " +
				"结束1、为了满足各位宝宝都能来参与张阳阳的活动及领取方便，故本次签唱会专辑仅在Owhat APP上发售； 2、本场签售会分为内场、外场，先签外场后签内场，内场外场均可参与面对面签名的福利！ 3、内场名额获取方式: 截止12月28日23:59，按照Owhat购买专辑数量从高到低排序，当购买数量相同时，按照付款时间排序，名额共100个； 4、福利部分现场/代签购买10张即可获得To签一张，购买15张及以上即可获得合影+to签！~跨年夜我们不见不散哦！5、本次签唱会只签海报，专辑制作完成在进行统一快递，请各位宝宝务必填写正确的地址，以免快递不能正常寄到.邮费到付！不能来到现场的宝宝们请在代签界面预定代签！支持邮费到付！Owhat为北京签售会粉丝独家应援平台，相关应援事宜请联系@Owhat微薄及@杭州小猪文化创意有限公司。专辑购买特别说明 【请务必仔细阅读】 1、本次购买时请务必填写【真实姓名】+【电话号码】+【收货地址】，便于面取时信息核对；线上所有信息以【下单时填写的信息】为准，不做任何修改，请大家一定要填写清楚，谢谢配合！ 2、付款方式有【微信】与【支付宝】两种。3、本活动因为专辑制作关系，只签海报，现场的宝宝专辑等专辑制作完成后进行统一快递，邮费到付！谢谢" +
				"【Somi SOLO出道专辑首批代购公告】本次专辑将以“定金+补款”形式进行、补款价格在最终国际国内运费确认后公开。实体专辑通过正规渠道购买、保证计入各大榜单销量数据。具体官方专辑配置暂时未出、米吧赠送特典请参考以下。定金均为50元/张、后续需要补款（分特典专和无特典专）截止日期 ：2018年12月中旬（暂定）购买方式：OW1️⃣特典专米吧特典：Set A (1张)：自拍卡+四格书签卡+镭射姓名贴纸+限定手幅Set B (2张)：Set A+易拉宝+镭射姓名条挂绳Set C (5张)：Set A+易拉宝+镭射姓名条挂绳+SNS自拍手册（后续会发特典设计图预览及实物图）2️⃣裸专无特典，购买三张送限定手幅。（注：随机掉落米吧旧特典）总数达到1000张，全体追加定制手机壳及神秘礼物。其他：??一旦支付成功不接受任何形式退款、未成年请在家长指导下进行购买。??首批保证有所有官方配置（如 预售特典）??最终国内发货前会进行地址确认，请多多留意微博以及QQ群。??下单后请加入米吧代购群：728342828（请备注下单id）" +
				"伍仁们，小伍最爱的是音乐和舞台作为他的歌迷，我们希望能守护他的音乐梦想为他保驾护航是我们的心愿。希望小伍的音乐被更多人知道并喜欢！我们想让他知道他是我们最爱的歌手为了我们的共同的初心我们唯有给他最好的应援，风雨同行，一路相扶坚持到底！此次零钱罐众筹资金全部用于小伍音乐打投应援！参与应援的伍仁欢迎加qq群号码:574079573" +
				"[粉丝会定制应援项目]北京LED <p>【提示 】 \n" +
				"<br></p><p>\n" +
				"<br></p><p>为了更好的为您服务，精准的为您提供应援方案，请直接联系对接的O妹再拍，如未有对接的O妹，请加Owhat应援部QQ并备注“咨询应援”：2521876629&nbsp;</p><p><font color=\"#ff6878\"><b>Owhat特享价，咨询O妹后再拍哦 </b></font></p><p><br></p><p>【支持地区】 \n" +
				"<br></p><p>\n" +
				"<br></p><p>北京 \n" +
				"<br></p><p><br></p><p>【应援用途】 \n" +
				"<br></p><p>\n" +
				"<br></p><p>生日应援/周年纪念日应援/演唱会应援/影视剧推广 \n" +
				"<br></p><p><br></p><p>【媒体资源汇总】\n" +
				"<br></p><p>\n" +
				"<br></p><p><font color=\"#38b3bb\">1.西单君太百货屏 LED&nbsp;</font></p><p><font color=\"#38b3bb\">2.西单老佛爷百货屏&nbsp;</font></p><p><font color=\"#38b3bb\">3.北京王府井步行街LED&nbsp;</font></p><p><font color=\"#38b3bb\">4.朝阳大悦城&nbsp;</font></p><p><font color=\"#38b3bb\">5.西单大悦城&nbsp;</font></p><p><font color=\"#38b3bb\">6.北京来福士&nbsp;</font></p><p><font color=\"#38b3bb\">7.北京东二环中汇广场&nbsp;</font></p><p><font color=\"#38b3bb\">8.北京东二环丰联广场&nbsp;</font></p><p><font color=\"#38b3bb\">9.北京国瑞购物中心&nbsp;</font></p><p><font color=\"#38b3bb\">10.北京东大桥路蓝岛大厦&nbsp;</font></p><p><font color=\"#38b3bb\">11.北京东三环富力广场&nbsp;</font></p><p><font color=\"#38b3bb\">12.崇文门搜秀商城屏（北向+西向）&nbsp;</font></p><p><font color=\"#38b3bb\">13.中关村鼎好电子大厦屏&nbsp;</font></p><p><font color=\"#38b3bb\">14.北京望京凯德Mall屏&nbsp;</font></p><p><font color=\"#38b3bb\">15.金宝街国旅大厦屏 \n" +
				"<br></font></p><p><br></p><p><br></p><p>【媒体介绍】\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284423358.jpg\" style=\"max-width:100%;\"></p><p>1.媒体名称 西单君太百货屏 （长安街可视面积最大户外LED 国内唯一4K级户外全高清LED）&nbsp;</p><p>位置 西单君太百货楼体&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 1000米&nbsp;</p><p>媒体规格 54.4（宽）×13.44m（高） = 731㎡&nbsp;</p><p>播放时间 07:00—24:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次2080+（单位:千人）&nbsp;</p><p>\n" +
				"<br></p><p>商业：最受时尚消费人群欢迎的西单商圈内最显眼、最大的户外LED大屏幕&nbsp;</p><p>干道：同时覆盖长安街与西单大街的车流及西单购物人流&nbsp;</p><p>商务：周边云集中国银行总行、光大银行北京分行等众多金融机构\n" +
				"<br></p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284538621.jpg\" style=\"max-width:100%;\"></p><p>2.媒体名称 西单老佛爷百货屏&nbsp;</p><p>位置 西单老佛爷百货商场楼体&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 800米&nbsp;</p><p>媒体规格 38.4m(W)*14.08m(H) = 537m²&nbsp;</p><p>播放时间 7：00-22：30&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次2080+（单位:千人）&nbsp;</p><p><br></p><p>商业：汇聚中高端群体，包括消费人群，西单及金融街商圈白领群体、全国各地来京政要、商旅人群等.大屏播放新华快讯新闻节目，中国电信、中关村、香奈儿等形象广告。法国百年高端品牌商场十月入驻西单，为首都中高端人群的聚焦地再添人气群体亦与工行目标受众重合度高。&nbsp;</p><p><br></p><p>\n" +
				"<img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284643728.jpg\" style=\"max-width:100%;\"></p><p>3.媒体名称  北京王府井步行街LED&nbsp;</p><p>位置  北京王府井步行街&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距  300米&nbsp;</p><p>媒体规格  17.02米(宽) x 10.75米(高)=183平方米&nbsp;</p><p>播放时间 9:00 - 22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次  1,051 +（单位:千人） \n" +
				"<br></p><p><br></p><p>商业： 商街内密布多型态、档次的购物、休闲中心,成为北京市民消费、休闲、文化、娱乐、餐饮、商务热门地；&nbsp;</p><p>干道：位置醒目,正对人流行进方向；&nbsp;</p><p>商务：位于有“金街”之称的王府井步行街入口处,靠近顶级办公、购物中心东方广场,面对长安街。\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284705821.jpg\" style=\"max-width:100%;\"><br></p><p>4.媒体名称 朝阳大悦城&nbsp;</p><p>位置 北京市朝阳区朝阳北路101号&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 400米&nbsp;</p><p>媒体规格 27.84m(W)×15.36m(H)=427.6平方米&nbsp;</p><p>播放时间 08:30-22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次 9680+（单位:千人）\n" +
				"<br></p><p><br></p><p>商业：得益于城市发展的重心东移，区域内已经云集了众多高档住宅，逐渐成为“中央生活区”，北京新贵人群、高级城市白领置业的首选之地。&nbsp;</p><p>干道：位于朝阳区东四环与东五环之间；地铁6号线青年路主站旁；北京东主干道朝阳北路北侧；地理位置得天独厚。东临星河湾四季会生态公园、世丰国际大厦和朝阳雅筑等高档商业、住宅小区；南靠朝阳北路；西邻珠江罗马家园、十里堡、晨光家园等；北倚星河湾小区。\n" +
				"<br></p><p><br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284819348.jpg\" style=\"max-width:100%;\"></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284843661.jpg\" style=\"max-width:100%;\"></p><p>5.媒体名称 西单大悦城南北屏&nbsp;</p><p>位置 西单大悦城南北位置&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 300米&nbsp;</p><p>媒体规格 南北屏：7.68米(宽) x 8.96米(高) x 2=138平方米&nbsp;</p><p>播放时间 9:00 - 23:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次1895+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业：位于北京最繁华的商业中心之一西单的中心地区，周边遍布购物娱乐中心、写字楼，如中国银行总行、中友百货等；&nbsp;</p><p>干道：正对人流密集的过街天桥及交通拥挤的道路；位置突出，引人注目；&nbsp;</p><p>商务：从两个不同角度辐射西单商圈核心区域。&nbsp;</p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284926350.jpg\" style=\"max-width:100%;\"></p><p>6.媒体名称 北京来福士&nbsp;</p><p>位置 北京东二环来福士广场&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 300米&nbsp;</p><p>媒体规格 16.5米(宽) x 10.5米(高)=173平方米&nbsp;</p><p>播放时间 9:00 - 22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次 2,269+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业：\u000B东二环上企业总部大楼、商场林立;工体北路上则遍布休闲娱乐场所,如保利剧院、工人体育场及三里屯的众多酒吧、俱乐部。&nbsp;</p><p>干道：位于东二环中央商务区,东二环与东直门外大街的交叉口,面对机场快轨(市区直达首都机场的轨道交通枢纽)； 在东二环及东直门大街上,媒体均有良好的可视性；&nbsp;</p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284964871.png\" style=\"max-width:100%;\"></p><p>7.媒体名称 北京东二环中汇广场LED&nbsp;</p><p>位置 北京东二环中汇广场&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 500米&nbsp;</p><p>媒体规格 28.16米(宽) x 11.52米(高)=324平方米&nbsp;</p><p>播放时间 9:00 - 22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次 2,211+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业： 中汇广场地处东二环商务圈核心位置，东二环与平安大街交汇处，东四十条桥西北角。毗邻亚洲最大的现代化立体综合交通枢纽——东直门交通枢纽，集公交、城铁、机场快速轨道、环线地铁为一体。 媒体周边写字楼云集，保利大厦、新保利大厦、南新仓大厦、南新仓国际大厦、船检大厦、鸿基大厦、文化部等。\n" +
				"<br></p><p><br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531284990009.png\" style=\"max-width:100%;\"></p><p>8.媒体名称 北京东二环丰联广场LED&nbsp;</p><p>位置 北京东二环丰联广场&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 500米&nbsp;</p><p>媒体规格 32.36米(宽) x 9.9米(高)=320平方米&nbsp;</p><p>播放时间 8:30 - 22:30&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次2,384+（单位:千人）&nbsp;</p><p>\n" +
				"<br></p><p>商业： 北京唯一政府、商圈结合的大型户外LED媒体，紧邻朝阳门区政府、外交部、使馆区，同时覆盖中国石油大厦、中国石化大厦、中国海洋石油大厦、昆泰国际中心、中国人寿大厦、泛利大厦、联合大厦、 MEN财富中心和丰联广场等高档写字楼中的世界500强公司； 工体时尚圈的必经之路。&nbsp;</p><p>干道： 位于东二环中央商务区,东二环与朝阳门外大街交叉口,靠近机场快轨(市区直达首都机场的轨道交通)，覆盖高人车流； \n" +
				"<br></p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285019473.png\" style=\"max-width:100%;\"></p><p>9.媒体名称 北京国瑞购物中心LED&nbsp;</p><p>位置 北京国瑞购物中心&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 22.4米(宽) x 12.768米(高)=286平方米&nbsp;</p><p>媒体规格 500米&nbsp;</p><p>播放时间 9:00 - 22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次670+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业： 位于北京南城重要商圈-崇文门商圈，雄倨北京市二环内，故宫、前门、明长城遗址等重要历史名胜古迹环抱周围。周边区域内汇集众多中高档写字楼及商场，如：新世界百货商场、新世界女子百货、搜秀、正仁大厦、京文大厦、大康大厦、新裕商务大厦、新成文化大厦，以及北京万怡酒店、崇文门饭店、新侨诺富特饭店等中高档酒店；&nbsp;</p><p>干道：  地处崇文门外大街最繁华地段，是北京南城连通长安街的重要交通咽喉，地铁2、5号线于此交汇，50多条公交线路，多条城市主干道环绕，覆盖巨大人、车流； 媒体位置醒目，高度适中，视线开阔，冲击力极强。&nbsp;</p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285054585.png\" style=\"max-width:100%;\"></p><p>10.媒体名称 北京东大桥路蓝岛大厦LED&nbsp;</p><p>位置 北京东大桥路蓝岛大厦&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 600米&nbsp;</p><p>媒体规格 31.6米(宽) x 12.7米(高)=402平方米&nbsp;</p><p>播放时间 7:00 - 23:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次2,200+（单位:千人）&nbsp;</p><p>\n" +
				"<br></p><p>商业： 位于蓝岛大厦东北角外墙体，地理位置优越，区域影响力大，覆盖周边朝阳门商圈、朝外商圈、新CBD、使馆商圈、工体商圈、三里屯商圈等京东重要商圈和商业中心，周边高档写字楼、商场林立，如昆泰大厦、金麒大厦、百富国际大厦、北京东方宫霄大厦、SOHO尚都写字楼、朝外SOHO、光华路SOHO、丰联广场、侨福芳草地、嘉里中心商场、世贸天阶、世界城、财富购物中心等，优质人群集中，目标受众汇聚；&nbsp;</p><p>干道： 地处工体东路、东大桥路、朝阳路、朝阳门外大街及朝阳北路交汇的五岔路口，地铁2号线、6号线于此交汇，有近16条公交线路的起始站，覆盖巨大人、车流； 正对视线方向，有效视距长，视角开阔，干扰遮挡少，视觉冲击力强。&nbsp;</p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285192537.png\" style=\"max-width:100%;\"></p><p>11.媒体名称 北京东三环富力广场LED&nbsp;</p><p>位置 朝阳区东三环中路65号北京富力广场，双井桥西北角&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 300米&nbsp;</p><p>媒体规格 15.68米(宽) x 12.85米(高)=201平方米&nbsp;</p><p>播放时间 8:00 - 22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次3,282+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业： 位于北京最大的商务群落国贸CBD商圈，是众多世界500强企业中国总部所在地，也是中央电视台、北京电视台传媒企业的新址，作为CBD的南大门，是相当主流的商业热点区域;&nbsp;</p><p>干道： 驻守东三环最拥堵的国贸商圈，是串联都市人群的生活主动脉，覆盖巨大人、车流；&nbsp;</p><p>商务： 涵括现代都市最为主流的消费主张，吸纳CBD上百万高消费人群；周边星级酒店、餐饮、娱乐齐全，成为京城优质的商业聚集地。&nbsp;</p><p>\n" +
				"<br></p><p>\n" +
				"<img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285377823.png\" style=\"max-width:100%;\"></p><p>12.媒体名称 崇文门搜秀商城屏（北向+西向）&nbsp;</p><p>位置 东城区崇文门外大街与花市大街交汇处 朝西 朝北&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 200米&nbsp;</p><p>媒体规格 7.7m（宽）×16.3m（高）=125.5㎡（西向）&nbsp;</p><p>                7.7m（宽）×16.3m（高）=125.5㎡（北向）&nbsp;</p><p>播放时间 07:30—22:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>媒体接触人次  8,397,204人次/周 \n" +
				"<br></p><p><br></p><p>商业：崇文门商圈定位为“引导时尚潮流的青年商业聚集地”，是京东区域购物、娱乐休闲、餐饮视听一体化的时尚消费中心，周围高端住宅云集，消费力水平高；&nbsp;</p><p>干道：“王字”交通网络实现四方纵横，地铁2号线、5号线汇集，双向特型LED大屏幕覆盖崇文门商圈车流与人流视线，特型显示屏有利于激发LED无限想象创意 \n" +
				"<br></p><p><br></p><p>\n" +
				"<img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285405072.png\" style=\"max-width:100%;\"></p><p>13.媒体名称 中关村鼎好电子大厦屏亚洲面积最大商用全彩户外LED)&nbsp;</p><p>位置 海淀区中关村大街与北四环交汇处 朝东北（仅播放静态画面）&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 2000米&nbsp;</p><p>媒体规格 44m（宽）×22.8m（高） = 1003.2㎡&nbsp;</p><p>播放时间 07:30—21:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次2000+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业：中关村商圈以“IT商贸”为核心，是北京最全面的多功能商业区&nbsp;</p><p>干道：同时覆盖北四环与中关村大街高峰拥堵时段车流及庞大消费人群，上百条公交线路及地铁4号线汇集于此&nbsp;</p><p>商务：行业收入水平较高的电子、科技等行业从业人群聚集，高档消费能力强，同时还覆盖了大量具有时尚消费欲望的学院消费群\n" +
				"<br></p><p><br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285430189.png\" style=\"max-width:100%;\"></p><p>14.媒体名称 北京望京凯德Mall屏&nbsp;</p><p>位置 朝阳区广顺北大街33号凯德Mall望京店&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 500&nbsp;</p><p>媒体规格 11.01m(W)x 35.07m(H)=386㎡&nbsp;</p><p>播放时间 9:00- 17:00（试运营期间）&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次350+（单位:千人） \n" +
				"<br></p><p><br></p><p>商业： 凯德MALL是集购物、餐饮、娱乐于一体的望京地区繁华商场，媒体周边云集多个大型商业设施、高档住宅及西门子等跨国公司总部。&nbsp;</p><p>干道： 地处进入望京两大主路广顺大街及阜通西大街之黄金交叉，毗邻京承高速公路和首都国际机场高速公路，交通便利。无论购物或出行，此位置是必经之地，地段极佳。&nbsp;</p><p>商务：更聚集了众多具有高消费能力及社会影响力的IT届精英和大批商务人士以及白领群体。\n" +
				"<br></p><p>\n" +
				"<br></p><p><img src=\"https://qimage.owhat.cn/prod/shop/cover/1531285453239.png\" style=\"max-width:100%;\"></p><p>15.媒体名称 金宝街国旅大厦屏&nbsp;</p><p>位置 东城区 金宝街国旅大厦正门上方墙体朝东北 弧形屏&nbsp;</p><p>媒体标注 地标/繁华商圈&nbsp;</p><p>有效视距 500米&nbsp;</p><p>媒体规格 14.616m（宽）×23.8m（高）=347.9㎡&nbsp;</p><p>播放时间 08:00—20:00&nbsp;</p><p>发布频次 60次/天&nbsp;</p><p>日媒体接触人次700+（单位:千人）&nbsp;</p><p>\n" +
				"<br></p><p>商业：金宝街奢侈品名店汇集，豪车展览店面众多，具有“中国最高端商业街”的著称，是名副其实的超高端消费场所&nbsp;</p><p>干道：地处东单北大街与金鱼胡同和金宝街三街交汇处，人车流量大且受众高端，其次十字路口的双红灯设置使得屏幕拥有更加充足的等待关注时间&nbsp;</p><p>商务：金宝街毗邻东城区政务中心办公区和外交部街，政务人事聚集具有浓郁的政务、商务气息和众多相关来往穿梭人群\n" +
				"<br></p><p><br></p><p>【注意事项】&nbsp;</p><p>1）请提前20个工作日以上预定档期，特殊节日提前1-2个月，咨询制作要求。&nbsp;</p><p>2）一次性结款,</p><p>3）其他需求请联系O妹哦</p><p>\n" +
				"<br></p><p>【后续服务】&nbsp;</p><p>1）应援活动结束后提供高质量图片及O妹应援快报；&nbsp;</p><p>2）Owhat官微与粉丝站微博进行互动；  \n" +
				"<br>\n" +
				"<br></p><p><br></p> ";
		Set<String> set = filter.getSensitiveWord(string, 1);
		long endTime = System.currentTimeMillis();
		System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
		System.out.println("待检测语句字数：" + string.length());
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共消耗时间为(ms)：" + (endTime - beginTime));
	}
}
