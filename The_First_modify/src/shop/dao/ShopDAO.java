package shop.dao;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import member.vo.MemberBean;
import shop.vo.ShopBean;

public class ShopDAO {
	// --------------------------------------------------
	// 싱글톤 패턴을 활용한 BoardDAO 인스턴스 생성 및 리턴
	private ShopDAO() {
	}

	private static ShopDAO instance = new ShopDAO();

	public static ShopDAO getInstance() {
		return instance;
	}
	// ----------------------------------------------------

	Connection con;

	// 외부로부터 Connection 객체를 전달받아 저장할 setConnection 메서드
	public void setConnection(Connection con) {
		this.con = con;
	}

	// ========== 상품 목록 조회 ===========
	public ArrayList<ShopBean> selectShopList() {
		System.out.println("ShopDAO - selectShopList()");

		// shop 테이블 내의 목록을 조회하여 ArrayList<ShopBean> 객체에 저장 후 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ShopBean> shopList = null;

		try {
			// 조회 결과(ResultSet) 객체가 존재할 경우
			// 반복문을 사용하여 1개 상품 정보를 ShopBean 객체에 저장하고
			// ShopBean 객체를 ArrayList<ShopBean> 객체에 저장 반복
			String sql = "SELECT * FROM shop";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			shopList = new ArrayList<ShopBean>();

			while (rs.next()) {
				ShopBean shopBean = new ShopBean(rs.getString("product_cod"), rs.getString("buyer_id"),
						rs.getString("product_name"), rs.getInt("price"), rs.getInt("stock"),
						rs.getString("product_image"), rs.getString("product_info"), rs.getInt("purchase_count"),
						rs.getDate("date"), rs.getInt("qty"), rs.getString("barcode_image")

				);

				shopList.add(shopBean);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("selectShopList() 에러! - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}

		return shopList;
	}

	// =========== 상품 상세 정보 조회 ============
	public ShopBean selectShop(String product_cod) {
		System.out.println("ShopDAO - selectShop()");

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// product_cod 값에 해당하는 레코드 조회 후 ShopBean 객체에 저장
		ShopBean shopBean = null;

		try {
			String sql = "SELECT * FROM shop WHERE product_cod=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product_cod);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				shopBean = new ShopBean(rs.getString("product_cod"), rs.getString("buyer_id"),
						rs.getString("product_name"), rs.getInt("price"), rs.getInt("stock"),
						rs.getString("product_image"), rs.getString("product_info"), rs.getInt("purchase_count"),
						rs.getDate("date"), rs.getInt("qty"), rs.getString("barcode_image")

				);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("selectShop() 에러! - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}

		return shopBean;
	}

	// =============== 상품 등록 ================
	public int insertProduct(ShopBean shopBean) {
		System.out.println("ShopDAO - insertProduct()");
		int insertCount = 0; // 작업 수행 결과 저장할 변수

		PreparedStatement pstmt = null;

		try {
			String info = "";
			if (shopBean.getProduct_info().contains("\r\n")) {
				info = shopBean.getProduct_info().replace("\r\n", "<br>");
			} else {
				info = shopBean.getProduct_info();
			}

			String sql = "INSERT INTO shop VALUES (?,null,?,?,?,?,?,null,null,now(),?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, shopBean.getProduct_cod());
			pstmt.setString(2, shopBean.getProduct_name());
			pstmt.setInt(3, shopBean.getPrice());
			pstmt.setInt(4, shopBean.getStock());
			pstmt.setString(5, shopBean.getProduct_image());
			pstmt.setString(6, info);
			pstmt.setString(7, shopBean.getBarcode_image());

			insertCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insertProduct() 에러 : " + e.getMessage());
		} finally {
			close(pstmt);
		}

		return insertCount;
	}

	// =============== 상품 수정 ================
	public int updateProduct(ShopBean product) {
		System.out.println("ShopDAO - updateProduct()");
		// 상품코드(product_cod)에 해당하는 게시물에 전달받은 수정 항목을 업데이트
		int updateCount = 0;

		PreparedStatement pstmt = null;

		try {
				String sql = "UPDATE shop SET product_name=?,price=?,stock=?,purchase_count=?,product_info=?,barcode_image=? WHERE product_cod=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product.getProduct_name());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getPurchase_count());
			pstmt.setString(5, product.getProduct_info());
//			pstmt.setString(6, product.getProduct_image());
			pstmt.setString(6, product.getBarcode_image());
			pstmt.setString(7, product.getProduct_cod());
			

			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return updateCount;
	}
	
	// =============== 상품 삭제 ================
	public int deleteProduct(String product_cod) {
		int deleteCount = 0;

		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM shop WHERE product_cod=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product_cod);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return deleteCount;
	}

	// ==================포인트 증가====================
	public int increasePoint(MemberBean shop) {
		System.out.println("ShopDAO - increasePoint()");

		int updateCount = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int cp = 0;

//			System.out.println(shop.getNickname());
//			System.out.println(shop.getCp());

		try {
			// 현재 cp 값 조회
			String sql = "SELECT cp FROM member WHERE nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shop.getNickname());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cp = rs.getInt("cp");
			}

			// 증가되는 cp 값 증가
			sql = "UPDATE member SET cp=?+? WHERE nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cp);// 현재 cp
			pstmt.setInt(2, shop.getCp());// 추가되는 cp
			pstmt.setString(3, shop.getNickname());
			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return updateCount;
	}

	// ==================포인트 차감====================
	public int deductPoint(MemberBean mb, ShopBean sb) {
		System.out.println("ShopDAO - deductPoint()");

		int updateCount = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int cp = 0;

		try {
			// 현재 cp 값 조회
			String sql = "SELECT cp FROM member WHERE nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getNickname());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cp = rs.getInt("cp");
			}

			// 차감되는 cp 값 차감
			// 현재cp >= 차감cp 이면 정상적으로 차감
			if (cp >= mb.getCp()) {
				sql = "UPDATE member SET cp=?-? WHERE nickname=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cp);// 현재 cp
				pstmt.setInt(2, mb.getCp());// 차감되는 cp
				pstmt.setString(3, mb.getNickname());
				updateCount = pstmt.executeUpdate();
//					System.out.println("포인트 차감 성공 ");

				// 현재cp < 차감cp 이면 차감안함
			} else if (cp < mb.getCp()) {
				updateCount = 0;
//					System.out.println("포인트 부족");
			}

			// 재고 차감, 판매량 증가
			if (updateCount == 1) {
				sql = "UPDATE shop SET stock=stock-1, purchase_count=purchase_count+1 WHERE product_cod=?";
				pstmt = con.prepareStatement(sql);
//					pstmt.setInt(1, sb.getStock());
//					pstmt.setInt(2, sb.getPurchase_count());
				pstmt.setString(1, sb.getProduct_cod());
				updateCount = pstmt.executeUpdate();
//					System.out.println("재고 차감, 판매량 증가 성공");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return updateCount;
	}

}
