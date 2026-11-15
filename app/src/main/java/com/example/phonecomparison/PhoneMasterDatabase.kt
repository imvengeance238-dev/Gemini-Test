package com.example.phonecomparison

import androidx.compose.runtime.Immutable

enum class PhoneSegment(val label: String) {
    ALL("সব রেঞ্জ"),
    BUDGET("বাজেট (১৫k-২৫k)"),
    MID_RANGE("মিড-রেঞ্জ (৩০k-৬৫k)"),
    FLAGSHIP("ফ্ল্যাগশিপ (১ লক্ষ+)")
}

@Immutable
data class Phone(
    val id: String,
    val brand: String,
    val name: String,
    val segment: PhoneSegment,
    val priceBdt: Int,
    val priceFormatted: String,
    val displaySize: String,
    val displayType: String,
    val refreshRate: String,
    val processor: String,
    val ramStorage: String,
    val batteryCapacity: String,
    val chargingSpeed: String,
    val osVersion: String,
    val uiSoftware: String,
    val mainCamera: String,
    val selfieCamera: String,
    val weightAndBuild: String,
    val highlight: String,
    val accentColorHex: Long
)

val masterPhonesList = listOf(
    // --- BUDGET SEGMENT ---
    Phone("b1", "Samsung", "Samsung Galaxy A16 5G", PhoneSegment.BUDGET, 21999, "৳২১,৯৯৯", "6.7 ইঞ্চি", "Super AMOLED, FHD+", "90Hz", "Exynos 1330", "6GB/128GB", "5000 mAh", "25W Fast", "Android 15", "One UI 7", "50MP + 5MP + 2MP", "13MP", "200g, IP54", "৬ বছরের আপডেট ও সুপার অ্যামোলেড", 0xFF2563EB),
    Phone("b2", "Xiaomi", "Xiaomi Redmi Note 14 4G", PhoneSegment.BUDGET, 23999, "৳২৩,৯৯৯", "6.67 ইঞ্চি", "AMOLED, 1800 nits", "120Hz", "Helio G99-Ultra", "8GB/128GB", "5500 mAh", "33W Turbo", "Android 14/15", "HyperOS", "108MP + 2MP + 2MP", "20MP", "196.5g, IP54", "১০৮ মেগাপিক্সেল ক্যামেরা", 0xFFEA580C),
    Phone("b3", "Infinix", "Infinix Hot 50 Pro+", PhoneSegment.BUDGET, 21999, "৳২১,৯৯৯", "6.78 ইঞ্চি", "3D-Curved AMOLED", "120Hz", "Helio G100", "8GB/256GB", "5000 mAh", "33W Fast", "Android 14", "XOS 14.5", "50MP + 2MP", "13MP", "162g, 6.8mm Slim", "বাজেটে পাতলা কার্ভড ডিসপ্লে", 0xFF059669),
    Phone("b4", "Realme", "Realme C75", PhoneSegment.BUDGET, 19999, "৳১৯,৯৯৯", "6.72 ইঞ্চি", "IPS LCD, FHD+", "90Hz", "Helio G92 Max", "8GB/128GB", "6000 mAh", "45W SuperVOOC", "Android 14", "Realme UI 5.0", "50MP", "8MP", "196g, IP69 Waterproof", "IP69 ওয়াটারপ্রুফ ও ৬০০০ mAh ব্যাটারি", 0xFFD97706),
    Phone("b5", "Honor", "Honor X7c", PhoneSegment.BUDGET, 22999, "৳২২,৯৯৯", "6.77 ইঞ্চি", "TFT LCD, 850 nits", "120Hz", "Snapdragon 685", "8GB/256GB", "6000 mAh", "35W SuperCharge", "Android 14", "MagicOS 8.0", "108MP + 2MP", "8MP", "194g, IP64", "১০৮MP ক্যামেরা ও ৬০০০ mAh ব্যাটারি", 0xFF0284C7),
    Phone("b6", "Tecno", "Tecno Pova 6", PhoneSegment.BUDGET, 23500, "৳২৩,৫০০", "6.78 ইঞ্চি", "AMOLED", "120Hz", "Helio G99 Ultimate", "8GB/256GB", "6000 mAh", "70W Fast", "Android 14", "HiOS 14", "108MP", "32MP", "195g, LED Matrix", "এলইডি ম্যাট্রিক্স ব্যাক ও ৭০W চার্জ", 0xFF4F46E5),
    Phone("b7", "Oppo", "Oppo A60", PhoneSegment.BUDGET, 22499, "৳২২,৪৯৯", "6.67 ইঞ্চি", "IPS LCD", "90Hz", "Snapdragon 680", "8GB/256GB", "5000 mAh", "45W SuperVOOC", "Android 14", "ColorOS 14", "50MP + 2MP", "8MP", "186g, Military Drop", "মিলিটারি গ্রেড ড্রপ রেসিস্ট্যান্স", 0xFF10B981),
    Phone("b8", "Vivo", "Vivo Y28 4G", PhoneSegment.BUDGET, 21999, "৳২১,৯৯৯", "6.68 ইঞ্চি", "IPS LCD", "90Hz", "Helio G85", "8GB/128GB", "6000 mAh", "44W FlashCharge", "Android 14", "Funtouch 14", "50MP + 2MP", "8MP", "199g, IP64", "৬০০০ mAh বিশাল ব্যাটারি", 0xFF6366F1),
    Phone("b9", "Infinix", "Infinix Note 40", PhoneSegment.BUDGET, 24999, "৳২৪,৯৯৯", "6.78 ইঞ্চি", "AMOLED", "120Hz", "Helio G99 Ultimate", "8GB/256GB", "5000 mAh", "45W + 20W Wireless", "Android 14", "XOS 14", "108MP (OIS)", "32MP", "190g, Magnetic Wireless", "ম্যাগনেটিক ওয়্যারলেস চার্জিং", 0xFF059669),
    Phone("b10", "Xiaomi", "Xiaomi Redmi 13 5G", PhoneSegment.BUDGET, 22999, "৳২২,৯৯৯", "6.79 ইঞ্চি", "IPS LCD", "120Hz", "Snapdragon 4 Gen 2", "8GB/128GB", "5030 mAh", "33W Fast", "Android 14", "HyperOS", "108MP + 2MP", "13MP", "205g, Gorilla Glass 3", "স্ন্যাপড্রাগন ৫জি বাজেট চিপসেট", 0xFFEA580C),

    // --- MID-RANGE SEGMENT ---
    Phone("m1", "Samsung", "Samsung Galaxy A56 5G", PhoneSegment.MID_RANGE, 49999, "৳৪৯,৯৯৯", "6.7 ইঞ্চি", "Super AMOLED, 1900 nits", "120Hz", "Exynos 1580", "8GB/256GB", "5000 mAh", "45W Super Fast", "Android 15", "One UI 7", "50MP (OIS) + 12MP + 5MP", "12MP", "198g, Aluminum, IP67", "মেটাল ফ্রেম ও ৪৫W চার্জিং", 0xFF2563EB),
    Phone("m2", "Xiaomi", "Redmi Note 14 Pro+ 5G", PhoneSegment.MID_RANGE, 44999, "৳৪৪,৯৯৯", "6.67 ইঞ্চি", "Curved AMOLED, 1.5K", "120Hz", "Snapdragon 7s Gen 3", "12GB/256GB", "6200 mAh", "90W HyperCharge", "Android 15", "HyperOS 2", "200MP (OIS) + 8MP + 2MP", "20MP", "205g, IP68/IP69", "২০০MP ক্যামেরা ও ৬২০০ mAh ব্যাটারি", 0xFFEA580C),
    Phone("m3", "OnePlus", "OnePlus Nord 4 5G", PhoneSegment.MID_RANGE, 46500, "৳৪৬,৫০০", "6.74 ইঞ্চি", "Fluid AMOLED, 1.5K", "120Hz", "Snapdragon 7+ Gen 3", "12GB/256GB", "5500 mAh", "100W SuperVOOC", "Android 15", "OxygenOS 15", "50MP (OIS) + 8MP", "16MP", "199.5g, Metal Body", "অল-মেটাল ইউনিবডি ডিজাইন", 0xFFE11D48),
    Phone("m4", "Google", "Google Pixel 9a", PhoneSegment.MID_RANGE, 58000, "৳৫৮,০০০", "6.3 ইঞ্চি", "P-OLED Actua", "120Hz", "Google Tensor G4", "8GB/128GB", "5100 mAh", "23W Wired + Wireless", "Android 15", "Stock Pixel UI", "48MP (OIS) + 13MP", "13MP", "186g, IP68", "৭ বছরের গুগল আপডেট ও এআই ক্যামেরা", 0xFF0D9488),
    Phone("m5", "Nothing", "Nothing Phone (3a) Pro", PhoneSegment.MID_RANGE, 43500, "৳৪৩,৫০০", "6.77 ইঞ্চি", "Flexible AMOLED", "120Hz", "Snapdragon 7s Gen 3", "12GB/256GB", "5000 mAh", "50W Fast Wired", "Android 15", "Nothing OS 3.1", "50MP (OIS) + 50MP Periscope", "50MP", "211g, Glyph LED", "গ্লিফ ডিজাইন ও পেরিস্কোপ জুম", 0xFF374151),
    Phone("m6", "Vivo", "Vivo V40 5G", PhoneSegment.MID_RANGE, 54999, "৳৫৪,৯৯৯", "6.78 ইঞ্চি", "Curved AMOLED", "120Hz", "Snapdragon 7 Gen 3", "12GB/256GB", "5500 mAh", "80W FlashCharge", "Android 14", "Funtouch 14", "50MP Zeiss (OIS) + 50MP", "50MP", "190g, IP68/IP69", "জেইস অপটিক্স ও অরা লাইট পোর্ট্রেট", 0xFF6366F1),
    Phone("m7", "Oppo", "Oppo Reno 12 Pro 5G", PhoneSegment.MID_RANGE, 57999, "৳৫৭,৯৯৯", "6.7 ইঞ্চি", "Curved AMOLED", "120Hz", "Dimensity 7300 Energy", "12GB/512GB", "5000 mAh", "80W SuperVOOC", "Android 14", "ColorOS 14.1", "50MP (OIS) + 8MP + 50MP Tele", "50MP", "180g, IP65", "এআই পোর্ট্রেট ও প্রিমিয়াম কার্ভড স্ক্রিন", 0xFF10B981),
    Phone("m8", "Realme", "Realme 13 Pro+ 5G", PhoneSegment.MID_RANGE, 48999, "৳৪৮,৯৯৯", "6.7 ইঞ্চি", "Curved AMOLED", "120Hz", "Snapdragon 7s Gen 2", "12GB/512GB", "5200 mAh", "80W SuperVOOC", "Android 14", "Realme UI 5.0", "50MP Sony Lytia + 50MP Periscope", "32MP", "190g, IP65", "সনি লিটিয়া সেন্সর ও পেরিস্কোপ ক্যামেরা", 0xFFD97706),
    Phone("m9", "Honor", "Honor 200 5G", PhoneSegment.MID_RANGE, 52999, "৳৫২,৯৯৯", "6.7 ইঞ্চি", "OLED", "120Hz", "Snapdragon 7 Gen 3", "12GB/512GB", "5200 mAh", "100W SuperCharge", "Android 14", "MagicOS 8.0", "50MP (OIS) + 50MP Telephoto", "50MP", "187g, Studio Portrait", "স্টুডিও পোর্ট্রেট মাস্টার ক্যামেরা", 0xFF0284C7),
    Phone("m10", "Xiaomi", "Poco X6 Pro 5G", PhoneSegment.MID_RANGE, 39999, "৳৩৯,৯৯৯", "6.67 ইঞ্চি", "AMOLED 1.5K", "120Hz", "Dimensity 8300 Ultra", "12GB/512GB", "5000 mAh", "67W Turbo", "Android 14", "HyperOS", "64MP (OIS) + 8MP + 2MP", "16MP", "186g, WildBoost", "গেমিংয়ে অসাধারণ ডায়মেনসিটি ৮৩০০ আল্ট্রা", 0xFFEA580C),

    // --- FLAGSHIP SEGMENT ---
    Phone("f1", "Samsung", "Samsung Galaxy S25 Ultra", PhoneSegment.FLAGSHIP, 148000, "৳১,৪৮,০০০", "6.9 ইঞ্চি", "Dynamic AMOLED 2X, Armor", "1-120Hz", "Snapdragon 8 Elite", "12GB/512GB", "5000 mAh", "45W Wired + Wireless", "Android 15", "One UI 7 + S-Pen", "200MP + 50MP (5x) + 10MP (3x) + 50MP", "12MP", "218g, Titanium, IP68", "টাইটানিয়াম বডি, এস-পেন ও ২০০MP ক্যামেরা", 0xFF4338CA),
    Phone("f2", "Apple", "Apple iPhone 16 Pro Max", PhoneSegment.FLAGSHIP, 158000, "৳১,৫৮,০০০", "6.9 ইঞ্চি", "Super Retina XDR OLED", "1-120Hz ProMotion", "Apple A18 Pro", "8GB/256GB", "4685 mAh", "30W+ PD + MagSafe", "iOS 18", "iOS 18 + AI", "48MP Fusion + 12MP Periscope + 48MP", "12MP TrueDepth", "227g, Titanium, IP68", "এ১৮ প্রো চিপ ও ৪কে ১২০এফপিএস ভিডিও", 0xFF1F2937),
    Phone("f3", "OnePlus", "OnePlus 13 5G", PhoneSegment.FLAGSHIP, 105000, "৳১,০৫,০০০", "6.82 ইঞ্চি", "BOE X2 LTPO AMOLED 2K", "1-120Hz", "Snapdragon 8 Elite", "16GB/512GB", "6000 mAh", "100W Wired + 50W Wireless", "Android 15", "OxygenOS 15", "50MP Sony + 50MP Periscope + 50MP", "32MP", "210g, IP68 & IP69", "৬০০০ mAh সিলিকন ব্যাটারি ও হ্যাসেলব্ল্যাড ক্যামেরা", 0xFFB91C1C),
    Phone("f4", "Google", "Google Pixel 9 Pro XL", PhoneSegment.FLAGSHIP, 128000, "৳১,২৮,০০০", "6.8 ইঞ্চি", "LTPO OLED Super Actua", "1-120Hz", "Google Tensor G4", "16GB/256GB", "5060 mAh", "37W Wired + Wireless", "Android 15", "Pixel UI + Gemini AI", "50MP + 48MP Periscope (5x) + 48MP", "42MP AF", "221g, Aluminum, IP68", "১৬GB র‍্যাম ও নেটিভ জেমিনি অ্যাডভান্সড এআই", 0xFF047857),
    Phone("f5", "Xiaomi", "Xiaomi 15 Ultra", PhoneSegment.FLAGSHIP, 135000, "৳১,৩৫,০০০", "6.73 ইঞ্চি", "LTPO AMOLED 2K", "1-120Hz", "Snapdragon 8 Elite", "16GB/512GB", "6000 mAh", "90W Wired + 80W Wireless", "Android 15", "HyperOS 2", "50MP 1-inch + 200MP Periscope + 50MP + 50MP", "32MP", "226g, Leica Optics, IP68", "১-ইঞ্চি সেন্সর ও ২০০MP লাইকা পেরিস্কোপ জুম", 0xFF7C3AED),
    Phone("f6", "Vivo", "Vivo X200 Pro", PhoneSegment.FLAGSHIP, 115000, "৳১,১৫,০০০", "6.78 ইঞ্চি", "LTPO AMOLED 2K", "1-120Hz", "Dimensity 9400", "16GB/512GB", "6000 mAh", "90W Wired + 30W Wireless", "Android 15", "Funtouch 15", "50MP 1-inch + 200MP Zeiss Periscope + 50MP", "32MP", "223g, IP68/IP69", "ডাইমেনসিটি ৯৪০০ ও জেইস ক্যামেরা ফ্ল্যাগশিপ", 0xFF6366F1),
    Phone("f7", "Honor", "Honor Magic 7 Pro", PhoneSegment.FLAGSHIP, 125000, "৳১,২৫,০০০", "6.8 ইঞ্চি", "LTPO OLED 1.5K", "1-120Hz", "Snapdragon 8 Elite", "16GB/512GB", "5850 mAh", "100W Wired + 80W Wireless", "Android 15", "MagicOS 9.0", "50MP Variable + 200MP Periscope + 50MP", "50MP + TOF", "223g, IP68/IP69", "ভ্যারিয়েবল অ্যাপারচার ও ৫৯০০ mAh ব্যাটারি", 0xFF0284C7),
    Phone("f8", "Sony", "Sony Xperia 1 VI", PhoneSegment.FLAGSHIP, 140000, "৳১,৪০,০০০", "6.5 ইঞ্চি", "4K HDR OLED (19.5:9)", "120Hz", "Snapdragon 8 Gen 3", "12GB/256GB", "5000 mAh", "30W USB-PD", "Android 14", "Sony Xperia UI", "48MP Exmor T + 12MP Continuous Zoom", "12MP", "192g, IP68, 3.5mm Jack", "প্রফেশনাল আলফা ক্যামেরা ও ৩.৫মিমি অডিও জ্যাক", 0xFF1E293B),
    Phone("f9", "Oppo", "Oppo Find X7 Ultra", PhoneSegment.FLAGSHIP, 118000, "৳১,১৮,০০০", "6.82 ইঞ্চি", "LTPO AMOLED 2K", "120Hz", "Snapdragon 8 Gen 3", "16GB/512GB", "5000 mAh", "100W Wired + 50W Wireless", "Android 14", "ColorOS 14", "Dual Periscope: 50MP (3x) + 50MP (6x) + 50MP 1-inch", "32MP", "221g, IP68", "বিশ্বের প্রথম ডুয়াল পেরিস্কোপ টেলিফটো লেন্স", 0xFF10B981),
    Phone("f10", "Huawei", "Huawei Pura 70 Ultra", PhoneSegment.FLAGSHIP, 135000, "৳১,৩৫,০০০", "6.8 ইঞ্চি", "OLED", "120Hz", "Kirin 9010", "16GB/512GB", "5200 mAh", "100W Wired + 80W Wireless", "HarmonyOS 4.2", "EMUI", "50MP Retractable 1-inch + 40MP UW + 50MP Macro", "13MP", "226g, IP68", "রিট্র্যাক্টেবল মেকানিক্যাল ১-ইঞ্চি ক্যামেরা", 0xFFDC2626)
)
