package com.demo.grpc.main.domain.member.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp

@Table(name = "n_member")
data class MemberEntity(

	@Id
	var seq: Long,
	var name: String,
	var type: String,
	var gender: String,
	var email: String,
	var birthDate: String,
	var cellNo: String,
	var ci: String,
	var status: String,
	var description: String,
	var privateInfoAgree: String,
	var marketingAgree: String,
	var marketingAgreeAt: Timestamp,
	var residentCollectAgreeAt: Timestamp,
	var residentRegistrationNumber1: String,
	var encryptResidentRegistrationNumber1: String,
	var residentRegistrationNumber2: String,
	var encryptResidentRegistrationNumber2: String,
	var awakeAt: Timestamp,
	var createdAt: Timestamp,
	var updatedAt: Timestamp,
	var nation: String,
	var referralCode: String,
	var referralSeq: Long
) {
//	fun toMemberVo(): MemberVo {
//		return MemberVo(
//			seq = this.seq,
//			name = this.name,
//			type = this
}
