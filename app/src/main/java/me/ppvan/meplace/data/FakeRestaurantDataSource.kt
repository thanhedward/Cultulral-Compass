package me.ppvan.meplace.data

import me.ppvan.meplace.R
object


FakeRestaurantDataSource {
    val dummyRestaurant = listOf(
        Restaurant(
            id=1,
            name="Hong Hoai's Vietnamese Restaurant & Vegan Food",
            location="20 P. Bát Đàn, Hàng Bồ, Hoàn Kiếm, Hà Nội",
            rate="4",
            description="Một trong những địa điểm ẩm thực được yêu thích nhất của Thành phố Hà Nội đối với cả du khách trong và ngoài nước. Thực đơn của chúng tôi bao gồm nhiều món ăn truyền thống Việt Nam phong phú, bao gồm các món đặc sản độc đáo từ ba miền Bắc, Trung, Nam.",
            picture=R.drawable.res1,
            webPath = "https://honghoaisrestaurant.com/",
        ),
        Restaurant(
            id=2,
            name="Era Restaurant- Vietnamese cuisine",
            location="48 P. Mã Mây, Hàng Buồm, Hoàn Kiếm, Hà Nội",
            rate="3",
            description = "Nằm ở trung tâm Khu Phố Cổ Hà Nội, nhà hàng của chúng tôi nổi tiếng là Ẩm thực Việt Nam chính thống nhất với hương vị truyền thống độc đáo, dịch vụ hoàn hảo và không gian cổ điển tuyệt vời với đầy những hình ảnh xưa khiến bạn nhớ về một Hà Nội cổ kính và xinh đẹp.",
            picture=R.drawable.res2,
            webPath = "http://www.erarestaurant.com/"
        ),
        Restaurant(
            id=3,
            name="Gia Ngư Restaurant",
            location="27-29 Gia Ngu Street, Hoan Kiem Dist, Hanoi City, Hà Nội",
            rate="4",
            description = "Tọa lạc tại một vị trí thuận tiện trên tầng hầm rộng lớn, Nhà hàng Gia Ngư là nhà hàng đầu tiên ra đời từ đầu năm 2000. Vào thời điểm đó, bối cảnh nhà hàng ở Hà Nội thiếu khái niệm về những nhà hàng quy mô nhỏ cung cấp dịch vụ ăn uống bình dân cao cấp và sự chú ý đến từng chi tiết. Kể từ khi ra mắt, địa điểm này đã trở thành địa điểm dẫn đầu xu hướng ăn uống ở Việt Nam. Tông màu xanh thép và xám ấm áp bổ sung cho đồ nội thất phòng ăn màu nâu sẫm và ánh sáng ấm áp mang đến bầu không khí ấm cúng, tinh tế. Quang cảnh thành phố tuyệt đẹp nhìn ra Hồ Hoàn Kiếm và chỉ cách 2 phút đi bộ, Sông Hồng trải dài và những cây cầu cũng như đường chân trời ngày càng mở rộng của Hà Nội tạo nên khung cảnh hoàn hảo, đặc biệt là vào ban đêm",
            picture=R.drawable.res3,
            webPath = "https://giangu-restaurant.com/"
        )
    )
}