package com.bookshop01.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.order.service.ApiService01;

@Controller
public class KakaoController2 {

	@Autowired
	private ApiService01 apiService01;

	@RequestMapping(value = "/test/kakaoPay.do", method = RequestMethod.POST)
	public ModelAndView kakaoPay(@RequestParam Map<String, String> map) throws Exception {
		ModelAndView mav = new ModelAndView();

		// 주문 DB에 저장 ...

		System.out.println(map.toString());
//		{ordr_idxx=20230502101712KK0638, good_name=����� ������, good_mny=100, buyr_name=�׽�Ʈ��, site_cd=A8QOB, req_tx=pay, pay_method=100000000000, currency=410, kakaopay_direct=Y, module_type=01, ordr_chk=20230502101712KK0638|100, param_opt_1=, param_opt_2=, param_opt_3=, res_cd=0000, res_msg=����, enc_info=1ek2ZPvycejXaigGoeD2v7ZXUP3x0PBLV7EzdWnXxF0XHpjVliY-tXR4bmdB07Tu0tQhG.4VMCZvOOlQVfoBy5e4.Nekwl9.4KzxI1ynU5YhGqDnmgYv03kn7h2nI34yNUlVaECGRwxX8WmrReDNQWO-leMcIm-QBgZkLYKbI8kGmUCan5HGiMnxX-MVfV6Uec0ueLAvkcX__, enc_data=3OgufPlUNtNep.J7OIGSSccwKjz14RHSSH.oP8zWzLqr1h83kTy7Wav1TPuUfZ3jCe7Ie8TD1oV6kPSRFMdEzK3mjJNXLtXkiLmdooLidDFkjaroraTdadEvldC8973lrROXIUOLn2i69O7f27YcyxdCiN7Y7q4znJbfp6484lmnhTCyuhyRgeOaf7-Z0DgK7S6GJoJHJEJ.-K8604TmTqh-ONOfE324J1RVP4poZvcCzp6VTno6puerBbTBgriyGKVIat3U6skashqxgiDKQTlweTqaf8MAdvuh0AuDsVl1ASqn6QOOV0WgEelfaQV6dXay6MeNhQBJ-LP86imVjVMqJztZ7fz.jPGCoumaQciVR2O-c3BmYDrTVlEf0uNR5ZTWnF8fcf2yPNMzhvZJTbQKzhGj3-MVLb00KGgUilbAwS.J7GSSoS5EtStXsp-v8oh7vr53X4uLtjoJ7A-sNGrXg9YYd2XQvwUEg03407.VM2ma5j0oBk4BV..c54TSWpYfHk9aMx9k16updNiNvNIoekb53L6d.8lavEcii9nlj3SGaRnUpZzZHxJKZjLL2DqW8LSpo55O9iPkz2zta2IB1bYZIz6Et.S6yFkBQ9Eo4LLsBeYpOQ4SeoaMZXZ0dzoSnD3G1bWXCUnc2w6kA4mJm5jBYfeE5JmWKugKkvEb_, ret_pay_method=CARD, tran_cd=00100000, use_pay_method=100000000000, card_pay_method=KAKAO_MONEY}

		// 4. 인증데이터로 결제 요청하기(rest api 사용)

		// 내가 api 통신시 보내야 되는(요청) 데이터 나열
		String res_cd = map.get("res_cd");
		String enc_info = map.get("enc_info");
		String enc_data = map.get("enc_data");
		String tran_cd = map.get("tran_cd");
		String card_pay_method = map.get("card_pay_method");
		String ordr_idxx = map.get("ordr_idxx");

		String id = "himedia"; // 발급된 계정
		String base = "https://api.testpayup.co.kr/";
		String path = "/ep/api/kakao/" + id + "/pay";

		String url = base + path;
		Map<String,String> map01 =new HashMap<String,String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map01.put("res_cd",res_cd);
		map01.put("enc_info",enc_info);
		map01.put("enc_data",enc_data);
		map01.put("tran_cd",tran_cd);
		map01.put("card_pay_method",card_pay_method);
		map01.put("ordr_idxx",ordr_idxx);
		//

		//
//		resultMap=apiService01.restApi(map01,url);
		
		//테스트 데이터(임의로 넣기)
		resultMap.put("responseCode","0000");
		resultMap.put("responseMsg","성공");
		resultMap.put("type","KAKAO_CARD"); //  KAKAO_MONEY
		resultMap.put("authDateTime","20230502171415");
		resultMap.put("amount","100");
		resultMap.put("cashReceipt","10");
		resultMap.put("authNumber","15616414561");
		resultMap.put("cardName","카카오카드");
		resultMap.put("cardNo","5136182311158171");
		resultMap.put("quota","0");


		
		
		// view 설정
		// 승인 성공 or 실패
		String responseCode = (String) resultMap.get("responseCode"); // 테스트중

		if ("0000".equals(responseCode)) {

			// 성공
			// 페이지 설정
			mav.setViewName("/kakao/kakaoResult");
			
			//화면에서 보여줄 값을 mav에 넣기
			mav.addObject("authDateTime",resultMap.get("authDateTime"));
			
			//통쨰로 보냄
			mav.addObject("resultMap",resultMap);
		} else {

			// 실패
			mav.setViewName("/kakao/kakaoResultFail");
			
		}

		// view 설정 아직 안했음

		return mav;
	}
}
