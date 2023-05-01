package com.bookshop01.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.service.ApiService01;
import com.bookshop01.order.service.OrderService;
import com.bookshop01.order.vo.OrderVO;

@Controller("orderController")
@RequestMapping(value="/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderVO orderVO;
	
	//@Autowired
	//private ApiService apiservice;
	@Autowired
	private ApiService01 apiService01;
	
	@RequestMapping(value="/orderEachGoods.do" ,method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO,
			                       HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		session=request.getSession();
		
		Boolean isLogOn=(Boolean)session.getAttribute("isLogOn");
		String action=(String)session.getAttribute("action");
		
		if(isLogOn==null || isLogOn==false){
			session.setAttribute("orderInfo", _orderVO);
			session.setAttribute("action", "/order/orderEachGoods.do");
			return new ModelAndView("redirect:/member/loginForm.do");
		}else{
			 if(action!=null && action.equals("/order/orderEachGoods.do")){
				orderVO=(OrderVO)session.getAttribute("orderInfo");
				session.removeAttribute("action");
			 }else {
				 orderVO=_orderVO;
			 }
		 }
		
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		List myOrderList=new ArrayList<OrderVO>();
		myOrderList.add(orderVO);

		MemberVO memberInfo=(MemberVO)session.getAttribute("memberInfo");
		
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberInfo);
		return mav;
	}
	
	@RequestMapping(value="/orderAllCartGoods.do" ,method = RequestMethod.POST)
	public ModelAndView orderAllCartGoods( @RequestParam("cart_goods_qty")  String[] cart_goods_qty,
			                 HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		Map cartMap=(Map)session.getAttribute("cartMap");
		List myOrderList=new ArrayList<OrderVO>();
		
		List<GoodsVO> myGoodsList=(List<GoodsVO>)cartMap.get("myGoodsList");
		MemberVO memberVO=(MemberVO)session.getAttribute("memberInfo");
		
		for(int i=0; i<cart_goods_qty.length;i++){
			String[] cart_goods=cart_goods_qty[i].split(":");
			for(int j = 0; j< myGoodsList.size();j++) {
				GoodsVO goodsVO = myGoodsList.get(j);
				int goods_id = goodsVO.getGoods_id();
				if(goods_id==Integer.parseInt(cart_goods[0])) {
					OrderVO _orderVO=new OrderVO();
					String goods_title=goodsVO.getGoods_title();
					int goods_sales_price=goodsVO.getGoods_sales_price();
					String goods_fileName=goodsVO.getGoods_fileName();
					_orderVO.setGoods_id(goods_id);
					_orderVO.setGoods_title(goods_title);
					_orderVO.setGoods_sales_price(goods_sales_price);
					_orderVO.setGoods_fileName(goods_fileName);
					_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
					myOrderList.add(_orderVO);
					break;
				}
			}
		}
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberVO);
		return mav;
	}	
	
	@RequestMapping(value="/payToOrderGoods.do" ,method = RequestMethod.POST)
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap,
			                       HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		//확인 :{receiver_name=이병승, receiver_hp1=010, receiver_hp2=2222, receiver_hp3=3333, receiver_tel1=02, receiver_tel2=1111, receiver_tel3=2222, delivery_address=우편번호:13547<br>도로명 주소:경기 성남시 분당구 고기로 25 (동원동)<br>[지번 주소:경기 성남시 분당구 동원동 79-1]<br>럭키빌딩 101호, delivery_message=, delivery_method=일반택배, gift_wrapping=no, pay_method=신용카드<Br>카드사:삼성<br>할부개월수:일시불, card_com_name=삼성, card_pay_month=일시불, pay_orderer_hp_num=해당없음, cardNo=1234123, expireMonth=1, expireYear=23, birthday=950801, cardPw=11}
		System.out.println("확인 :" + receiverMap.toString());
		
		//결제 승인 요청 변수(필수만 넣음)(변수명은 규격서내 이름과 일치하게 작성)
		String merchantId ="himedia"; //가맹점아이디
		String orderNumber = "";//주문번호
		String cardNo = "";//카드번호
		String expireMonth = "";
		String expireYear = "";
		String birthday = "";
		String cardPw = "";
		String amount = "";
		String quota = "";
		String itemName = "";
		String userName = "";
		String signature = "";
		String timestamp ="";
		String apiCertKey ="";//api 키
		
		orderNumber = "TEST_choi"; //주문번호 생성
		cardNo = receiverMap.get("cardNo"); //화면에서 받은값
		expireMonth = receiverMap.get("expireMonth");//화면에서 받은값
		expireYear = receiverMap.get("expireYear");//화면에서 받은값
		birthday = receiverMap.get("birthday");//화면에서 받은값
		cardPw = receiverMap.get("cardPw");//화면에서 받은값
		amount="1000";//화면에서 받은값
		quota="0";	//일시불
		itemName = "책";//화면에서 받은값
		userName = receiverMap.get("receiver_name");//화면에서 받은값
		apiCertKey="ac805b30517f4fd08e3e80490e559f8e";
		timestamp ="20230501112700";
//		예시)sha256_hash({merchantId}|{orderNumber}|{amount}|{apiCertKey}|{timestamp})
		signature =apiService01.encrypt(merchantId+"|"+orderNumber+"|"+amount+"|"+apiCertKey+"|"+timestamp); //서명값 
		
		//rest api를 라이브러리 써서 사용
		// 가장 평범한 통신은 httpURLconnection 으로 하는 통신 (x)
		String url ="https://api.testpayup.co.kr/v2/api/payment/"+merchantId+"/keyin2";
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//map에 다가 요청데이터 값들을 넣으시면 됩니다.
		map.put("merchantId",merchantId);
		map.put("orderNumber",orderNumber);
		map.put("cardNo",cardNo);
		map.put("expireMonth",expireMonth);
		map.put("expireYear",expireYear);
		map.put("birthday",birthday);
		map.put("cardPw",cardPw);
		map.put("amount",amount);
		map.put("quota",quota);
		map.put("itemName",itemName);
		map.put("userName",userName);
		map.put("signature", signature);
		map.put("timestamp",timestamp);
		
		
		returnMap=apiService01.restApi(map, url);
		
		System.out.println("카드결제 승인 응답 데이터 = "+ returnMap.toString());
		
		//응답값을 잘 받으면 수기결제(구인증) 연동 완료.
		
		
		
		//응답값을 잘 받으면
		
		//승인 성공 or 실패 
		String responseCode =  (String) returnMap.get("responseCode");
		
		if("0000".equals(responseCode)) {
			
			//위에값
			mav.addObject("returnMap",returnMap);
			mav.setViewName("/order/orderResult");
		}else { 
			
			mav.addObject("returnMap",returnMap);
			//실패
			mav.setViewName("/order/orderFail");
		}
		
		
		//결제하기 끝
		
		HttpSession session=request.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("orderer");
		String member_id=memberVO.getMember_id();
		String orderer_name=memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1()+"-"+memberVO.getHp2()+"-"+memberVO.getHp3();
		List<OrderVO> myOrderList=(List<OrderVO>)session.getAttribute("myOrderList");
		
		for(int i=0; i<myOrderList.size();i++){
			OrderVO orderVO=(OrderVO)myOrderList.get(i);
			orderVO.setMember_id(member_id);
			orderVO.setOrderer_name(orderer_name);
			orderVO.setReceiver_name(receiverMap.get("receiver_name"));
			
			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderVO.setReceiver_hp2(receiverMap.get("receiver_hp2"));
			orderVO.setReceiver_hp3(receiverMap.get("receiver_hp3"));
			orderVO.setReceiver_tel1(receiverMap.get("receiver_tel1"));
			orderVO.setReceiver_tel2(receiverMap.get("receiver_tel2"));
			orderVO.setReceiver_tel3(receiverMap.get("receiver_tel3"));
			
			orderVO.setDelivery_address(receiverMap.get("delivery_address"));
			orderVO.setDelivery_message(receiverMap.get("delivery_message"));
			orderVO.setDelivery_method(receiverMap.get("delivery_method"));
			orderVO.setGift_wrapping(receiverMap.get("gift_wrapping"));
			orderVO.setPay_method(receiverMap.get("pay_method"));
			orderVO.setCard_com_name(receiverMap.get("card_com_name"));
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderVO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num"));	
			orderVO.setOrderer_hp(orderer_hp);	
			myOrderList.set(i, orderVO); 
		}
		
	    orderService.addNewOrder(myOrderList);
		mav.addObject("myOrderInfo",receiverMap);
		mav.addObject("myOrderList", myOrderList);
		return mav;
	}
	

}
