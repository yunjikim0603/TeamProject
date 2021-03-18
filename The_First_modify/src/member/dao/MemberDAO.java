package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import member.vo.MemberBean;
import member.vo.SHA256;

import static db.JdbcUtil.*;

public class MemberDAO {
	private MemberDAO() {}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	Connection con = null;
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	// 로그인
	public int selectLoginMember(MemberBean member) {
		int loginResult = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT password FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				if(rs.getString(1).equals(member.getPassword())) {
					loginResult = 1;
				} else { 
					loginResult = -1;
				}
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginResult;
	}
	
	// 아이디 중복체크
	public int idChk(String id) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 

		try {
			String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return count;
	}
	
	// 닉네임 중복체크
	public int nicknameChk(String nickname) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 

		try {
			String sql = "SELECT COUNT(*) FROM member WHERE nickname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return count;
	}
	
	// 회원가입
	public int insertMember(MemberBean member) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());
			pstmt.setString(4, member.getEmail());
			pstmt.setInt(5, 0); // CP
			pstmt.setInt(6, 0); // LP
			pstmt.setInt(7, 1); // level
			pstmt.setString(8, SHA256.getSHA256(member.getEmail())); // email 인증
			pstmt.setInt(9, 0); // false
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	// 회원 가져오기
		public MemberBean getMember(String nickname) {
			con = getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MemberBean mb = new MemberBean();
			
			try {
				String sql = "SELECT * FROM member WHERE nickname = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					mb.setId(rs.getString("id"));
					mb.setNickname(rs.getString("nickname"));
					mb.setEmail(rs.getString("email"));
					mb.setCp(rs.getInt("cp"));
					mb.setLp(rs.getInt("lp"));
					mb.setLevel(rs.getInt("level"));
					mb.setDate(rs.getDate("date"));
					mb.setHearts(rs.getInt("hearts"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return mb;
		}
	
	// 정보수정
	public int updateMember(MemberBean mb) {
		PreparedStatement pstmt = null;
		int updateCount = 0;
		
		try {
			String sql = "UPDATE member SET password=?,nickname=?,email=? WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getPassword());
			pstmt.setString(2, mb.getNickname());
			pstmt.setString(3, mb.getEmail());
			pstmt.setString(4, mb.getId());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	// 회원삭제
	public int deleteMember(String id) {
		PreparedStatement pstmt = null;
		int updateCount = 0;
		
		try {
			String sql = "DELETE FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	// 닉네임 가져오기
	public String getUserNickname(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT nickname FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				return rs.getString(1); // 닉네임 반환
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return null; // 데이터베이스 오류
	}
	
	// Email 가져오기
	public String getUserEmail(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT email FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				return rs.getString(1); // 이메일 주소 반환
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return null; // 데이터베이스 오류
	}
	
	// Email 인증여부 확인
	public boolean getUserEmailChecked(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT emailChecked FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return rs.getBoolean(1); // 이메일 등록 여부 반환
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return false; // 데이터베이스 오류
	}

	// Email 인증 부여
	public boolean setUserEmailChecked(String id) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE member SET emailChecked = true WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return false;
	}
	
	///dain

	public int getHearts(String nickname) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int hearts = 0;
		
		try {
			String sql = "SELECT hearts FROM member WHERE nickname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				hearts = rs.getInt("hearts");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return hearts;
	}
	
	public int updateCP(String nickname, int article_CP) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE member SET CP=CP-? WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, article_CP);
			pstmt.setString(2, nickname);
			updateNum = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		// TODO Auto-generated method stub
		return updateNum;
	}

	public int updateMemberCP(String nickname, int CP) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE member SET CP=CP+? WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, CP);
			pstmt.setString(2, nickname);
			updateNum = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		// TODO Auto-generated method stub
		return updateNum;
	}
	
	public int selectCP(String nickname) {
		int userCP = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 글번호(board_num)에 해당하는 게시물 정보 조회
		try {
			String sql = "SELECT CP FROM member WHERE nickname=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 저장
//			if(rs.next()) { //while?
			if(rs.next()) {
				userCP = rs.getInt("CP");
								
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return userCP;
	}

	public int updateArticleLP(String nickname) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "UPDATE member SET LP=LP+20 WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			updateNum = pstmt.executeUpdate();
			
			if(updateNum>0) {
				sql = "SELECT LP,level FROM member WHERE nickname =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int level = rs.getInt("level");
					int LP = rs.getInt("LP");
					if(level!=3) {
						if(LP>=500) {
							sql = "UPDATE member SET level=2 WHERE nickname =?";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, nickname);
							updateNum = pstmt.executeUpdate();
						}
						
					}
					
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		

		return updateNum;
	}

	public int updateCommentLP(String nickname) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "UPDATE member SET LP=LP+10 WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			updateNum = pstmt.executeUpdate();
			if(updateNum>0) {
				sql = "SELECT LP,level FROM member WHERE nickname =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int level = rs.getInt("level");
					int LP = rs.getInt("LP");
					if(level!=3) {
						if(LP>=500) {
							sql = "UPDATE member SET level=2 WHERE nickname =?";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, nickname);
							updateNum = pstmt.executeUpdate();
						}
						
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		

		return updateNum;
	}

	public int minusArticletLP(String nickname) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "UPDATE member SET LP=LP-20 WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			updateNum = pstmt.executeUpdate();
			if(updateNum>0) {
				sql = "SELECT LP,level FROM member WHERE nickname =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					int LP = rs.getInt(1);
					if(LP<500) {
						sql = "UPDATE member SET level=1 WHERE nickname =?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, nickname);
						updateNum = pstmt.executeUpdate();
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		

		return updateNum;
	}


	public int minusCommentLP(String nickname) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "UPDATE member SET LP=LP-10 WHERE nickname =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			updateNum = pstmt.executeUpdate();
			if(updateNum>0) {
				sql = "SELECT LP FROM member WHERE nickname =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					int LP = rs.getInt(1);
					if(LP<500) {
						sql = "UPDATE member SET level=1 WHERE nickname =?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, nickname);
						updateNum = pstmt.executeUpdate();
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		

		return updateNum;
	}
	
	public ArrayList<MemberBean> selectMemberList() {
		
//		System.out.println("MemberDAO - selectMemberList()");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<MemberBean> memberList = null;
		
		try {
			String sql = "SELECT * FROM member ORDER BY date";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memberList = new ArrayList<MemberBean>();
			
			while(rs.next()) {
				MemberBean mb = new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPassword(rs.getString("password"));
				mb.setNickname(rs.getString("nickname"));
				mb.setEmail(rs.getString("email"));
				mb.setCp(rs.getInt("CP"));
				mb.setLp(rs.getInt("LP"));
				mb.setLevel(rs.getInt("level"));
				mb.setEmailHash(rs.getString("emailHash"));
				mb.setEmailChecked(rs.getBoolean("emailChecked"));
				mb.setDate(rs.getDate("date"));
				
				memberList.add(mb);
			}
		} catch (Exception e) {
			System.out.println("selectMemberList() 에러! - " + e.getMessage());
		} finally {
 			close(rs);
 			close(pstmt);
 		}
		return memberList;
	}



	public int updateMemberHeart(String nickname, int hearts) {
		int updateNum = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE member SET hearts=? WHERE nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, hearts);
			pstmt.setString(2, nickname);
			updateNum = pstmt.executeUpdate();
			if(updateNum>=0) {
				if(hearts>=100) {
    				sql = "UPDATE member SET level=3 WHERE nickname=?";
    				pstmt = con.prepareStatement(sql);
    				pstmt.setString(1, nickname);
    				updateNum = pstmt.executeUpdate();
    			}else {
    				sql = "UPDATE member SET level=2 WHERE nickname=?";
    				pstmt = con.prepareStatement(sql);
    				pstmt.setString(1, nickname);
    				updateNum = pstmt.executeUpdate();
    			}
				
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		

		return updateNum;
	}

}
























