package me.ppvan.meplace.ui.view.QuizGame

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

object QuestionDataBase {
    val question = listOf(
        "Điều gì là biểu tượng nổi tiếng của đền Hùng?",
        "Ai là vị vua nổi tiếng xây dựng Văn Miếu - Quốc Tử Giám?",
        "Tượng đài nào ở Hà Nội nổi tiếng với hình ảnh ngựa?",
        "Di tích nào dưới đây là ngôi chùa nổi tiếng ở Huế?",
        "Ai là vị vua xây dựng thành Đại La, ngày nay là Hà Nội?",
        "Ai là vị vua xây dựng thành Thăng Long, ngày nay là Hà Nội?",
        "Di tích nào ở Quảng Bình nổi tiếng với hình ảnh vòm đá tự nhiên?",
        "Di tích nào dưới đây là nơi nổi tiếng với hình ảnh ngọn hải đăng cổ nhất Việt Nam?",
        "Di tích nào dưới đây là ngôi chùa nổi tiếng với tượng Phật Bà?",
        "Di tích nào ở Hội An nổi tiếng với kiến trúc phong cách Nhật Bản?"
    )

    val options = listOf(
        listOf("Cây đa", "Quần thái", "Phù đổng", "Cô Bạch"),
        listOf("Lý Thánh Tông", "Lê Thái Tổ", "Lý Thường Kiệt", "Ngô Quyền"),
        listOf("Tượng Đức Bà", "Tượng Lý Thái Tổ", "Tượng Lý Thường Kiệt", "Tượng Ngọc Hồ"),
        listOf("Chùa Thiên Mụ", "Chùa Huế", "Chùa Linh Mụ", "Chùa Tây Phương"),
        listOf("Lý Thái Tổ", "Lý Thường Kiệt", "Ngô Quyền", "Lê Thánh Tông"),
        listOf("Lê Lợi", "Lý Thái Tổ", "Lý Thường Kiệt", "Nguyễn Huệ"),
        listOf("Hang Sơn Đoòng", "Hang Tiên", "Hang Ổng", "Hang Én"),
        listOf("Cửa Đại", "Cầu Đá", "Cầu Cảng", "Cầu Cổ"),
        listOf("Chùa Một Cột", "Chùa Tứ Pháp", "Chùa Linh Ứng", "Chùa Trấn Quốc"),
        listOf("Chùa Bái Đính", "Chùa Một Cột", "Chùa Linh Ứng", "Chùa Linh Phước")
    )

    val correctAnswer = listOf(
        "Cây đa",
        "Lý Thái Tổ",
        "Tượng Lý Thái Tổ",
        "Chùa Thiên Mụ",
        "Lý Thái Tổ",
        "Lê Lợi",
        "Hang Sơn Đoòng",
        "Cửa Đại",
        "Chùa Một Cột",
        "Chùa Một Cột"
    )

    fun createQuestions(): List<Question> {
        val questionTexts = question
        val answers = options
        val correctAnswers = correctAnswer

        return List(questionTexts.size) { index ->
            Question(
                question = questionTexts[index],
                options = answers[index],
                correctAnswer = correctAnswers[index]
            )
        }
    }
}
