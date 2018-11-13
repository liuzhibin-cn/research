package org.liuzhibin.research.mycat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.liuzhibin.research.mycat.domain.Member;
import org.liuzhibin.research.mycat.domain.MemberAccount;

@Mapper
public interface MemberDao {
	@Insert("insert into member (member_id, account, password, nickname, mobile, email, created_at) values (#{memberId}, #{account}, #{password}, #{nickname}, #{mobile}, #{email}, #{createdAt})")
	int createMember(Member member);
	
	@Insert("insert into member_account(account, account_hash, member_id) values (#{account}, #{accountHash}, #{memberId})")
	int createMemberAccount(MemberAccount ma);
	
	@Select("select count(*) from member_account where account=#{account} and account_hash=#{accountHash}")
	int isRegistered(@Param("account") String account, @Param("accountHash") int accountHash);
	
	@Select("select * from member_account where account=#{account} and account_hash=#{accountHash}")
	@ResultMap("memberAccount")
	MemberAccount getMemberAccount(@Param("account") String account, @Param("accountHash") int accountHash);
	
	@Select("select * from member where member_id=#{memberId}")
	@ResultMap("member")
	Member getMember(long memberId);
	
	@Insert("insert into member_order (member_id, order_id) values(#{memberId}, #{orderId})")
	int createMemberOrder(@Param("memberId") long memberId, @Param("orderId") long orderId);
	
	@Select("select order_id from member_order where member_id = #{memberId} limit #{offset}, #{count}")
	@ResultType(Long.class)
	List<Long> findMemberOrder(long memberId, int offset, int count);
}