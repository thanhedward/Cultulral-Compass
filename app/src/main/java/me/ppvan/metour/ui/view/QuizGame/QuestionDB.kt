package me.ppvan.metour.ui.view.QuizGame

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

object QuestionDataBase {
    private val question = listOf(
        "Điều gì là biểu tượng nổi tiếng của đền Hùng?",
        "Ai là vị vua nổi tiếng xây dựng Văn Miếu - Quốc Tử Giám?",
        "Tượng đài nào ở Hà Nội nổi tiếng với hình ảnh ngựa?",
        "Di tích nào dưới đây là ngôi chùa nổi tiếng ở Huế?",
        "Ai là vị vua xây dựng thành Đại La, ngày nay là Hà Nội?",
        "Ai là vị vua xây dựng thành Thăng Long, ngày nay là Hà Nội?",
        "Di tích nào ở Quảng Bình nổi tiếng với hình ảnh vòm đá tự nhiên?",
        "Di tích nào dưới đây là nơi nổi tiếng với hình ảnh ngọn hải đăng cổ nhất Việt Nam?",
        "Di tích nào dưới đây là ngôi chùa nổi tiếng với tượng Phật Bà?",
        "Di tích nào ở Hội An nổi tiếng với kiến trúc phong cách Nhật Bản?",
        "Ai là vị vua xây dựng thành Long Biên, ngày nay là Hà Nội?",
        "Ai là người xây dựng lâu đài Hồ Quý Ly ở Thanh Hóa?",
        "Tượng nào nổi tiếng với tác giả chân dung Bác Hồ?",
        "Ai là người thiết kế cầu Long Biên?",
        "Di tích nào ở Quảng Bình nổi tiếng với hình ảnh hang động đẹp nhất thế giới?",
        "Di tích nào dưới đây là nơi nổi tiếng với hình ảnh cầu gỗ đẹp nhất Việt Nam?",
        "Điểm nào nổi tiếng với hình ảnh vịnh Hạ Long, di sản thiên nhiên thế giới?",
        "Ai là người thiết kế chùa Bái Đính?",
        "Tượng nào nổi tiếng với tác giả đời thường?",
        "Ai là người thiết kế cầu Cầu Hàm Rồng ở Đà Nẵng?"
    )

    private val options = listOf(
        listOf("Cây đa", "Quần thái", "Phù đổng", "Cô Bạch"),
        listOf("Lý Thánh Tông", "Lê Thái Tổ", "Lý Thường Kiệt", "Ngô Quyền"),
        listOf("Tượng Đức Bà", "Tượng Lý Thái Tổ", "Tượng Lý Thường Kiệt", "Tượng Ngọc Hồ"),
        listOf("Chùa Thiên Mụ", "Chùa Huế", "Chùa Linh Mụ", "Chùa Tây Phương"),
        listOf("Lý Thái Tổ", "Lý Thường Kiệt", "Ngô Quyền", "Lê Thánh Tông"),
        listOf("Lê Lợi", "Lý Thái Tổ", "Lý Thường Kiệt", "Nguyễn Huệ"),
        listOf("Hang Sơn Đoòng", "Hang Tiên", "Hang Ổng", "Hang Én"),
        listOf("Cửa Đại", "Cầu Đá", "Cầu Cảng", "Cầu Cổ"),
        listOf("Chùa Một Cột", "Chùa Tứ Pháp", "Chùa Linh Ứng", "Chùa Trấn Quốc"),
        listOf("Chùa Bái Đính", "Chùa Một Cột", "Chùa Linh Ứng", "Chùa Linh Phước"),
        listOf("Lý Thái Tổ", "Lê Lợi", "Lý Công Uẩn", "Lý Phục Man"),
        listOf("Hồ Quý Ly", "Lê Lợi", "Trần Thái Tông", "Lê Thái Tổ"),
        listOf("Tượng Bác Hồ tại Bảo tàng Hồ Chí Minh", "Tượng Bác Hồ tại Bảo tàng Lịch sử Quân sự", "Tượng Bác Hồ tại Bảo tàng Dân tộc học", "Tượng Bác Hồ tại Bảo tàng Mỹ thuật Việt Nam"),
        listOf("Gustave Eiffel", "Nguyễn Văn Toản", "Trần Hưng Đạo", "Nguyễn Huệ"),
        listOf("Hang Sơn Đoòng", "Hang Én", "Hang Tiên", "Hang Ổng"),
        listOf("Cầu Thê Húc", "Cầu Cổ", "Cầu Long Biên", "Cầu Tràng Tiền"),
        listOf("Đỉnh Fansipan", "Đỉnh Bạch Mã", "Đỉnh Mẫu Sơn", "Đỉnh Ngũ Hành Sơn"),
        listOf("Ngô Minh Hòa", "Trần Văn Dược", "Nguyễn Phan Long", "Lê Văn Lợi"),
        listOf("Nguyễn Hàn", "Tạ Quang Bửu", "Phan Đình Phùng", "Võ Nguyên Giáp"),
        listOf("Trần Phú", "Nguyễn Văn Cừ", "Đinh Tiên Hoàng", "Hoàng Diệu"),
        listOf("Hang Sơn Đoòng", "Hang Én", "Hang Tiên", "Hang Ổng"),
        listOf("Cầu Thê Húc", "Cầu Cổ", "Cầu Long Biên", "Cầu Tràng Tiền"),
        listOf("Đỉnh Fansipan", "Đỉnh Bạch Mã", "Đỉnh Mẫu Sơn", "Đỉnh Ngũ Hành Sơn"),
        listOf("Ngô Minh Hòa", "Trần Văn Dược", "Nguyễn Phan Long", "Lê Văn Lợi"),
        listOf("Nguyễn Văn Hùng", "Phạm Hồng Hải", "Phan Đình Khải", "Trần Văn Đạo")
    )

    private val correctAnswer = listOf(
        "Cây đa",
        "Lê Thái Tổ",
        "Tượng Lý Thái Tổ",
        "Chùa Thiên Mụ",
        "Lý Thái Tổ",
        "Lê Lợi",
        "Hang Sơn Đoòng",
        "Cầu Cổ",
        "Chùa Một Cột",
        "Chùa Một Cột",
        "Lý Thái Tổ",
        "Hồ Quý Ly",
        "Tượng Bác Hồ tại Bảo tàng Lịch sử Quân sự",
        "Gustave Eiffel",
        "Hang Sơn Đoòng",
        "Cầu Thê Húc",
        "Đỉnh Fansipan",
        "Ngô Minh Hòa",
        "Phan Đình Khải",
        "Trần Văn Đạo"
    )



    fun createQuestions(): List<Question> {
        val randomNumbers = (0..14).shuffled().take(10)
        val questionTexts = question
        val answers = options
        val correctAnswers = correctAnswer

        return List(10) { index ->
            Question(
                question = questionTexts[randomNumbers[index]],
                options = answers[randomNumbers[index]],
                correctAnswer = correctAnswers[randomNumbers[index]]
            )
        }
    }
}
