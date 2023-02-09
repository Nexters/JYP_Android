package com.jyp.core_network.jyp.model

data class KakaoSignIn(
    val token: String,
    val id: Long,
    val connectedAt: String,
    val properties: UserProperties,
    val kakaoAccount: KakaoAccount?
) {
    data class UserProperties(
        val nickname: String,
        val profileImage: String,
        val thumbnailImage: String
    )

    data class KakaoAccount(
        val profileNicknameNeedsAgreement: Boolean,
        val profileImageNeedsAgreement: Boolean,
        val profile: Profile,
        val hasEmail: Boolean,
        val emailNeedsAgreement: Boolean,
        val hasAgeRange: Boolean,
        val ageRangeNeedsAgreement: Boolean,
        val hasBirthday: Boolean,
        val birthdayNeedsAgreement: Boolean,
        val hasGender: Boolean,
        val genderNeedsAgreement: Boolean
    ) {
        data class Profile(
            val nickName: String,
            val thumbnailImageUrl: String,
            val profileImageUrl: String,
            val isDefaultImage: Boolean
        )
    }
}
