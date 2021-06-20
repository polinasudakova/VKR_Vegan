package com.vegan

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegan.models.Product
import com.vegan.models.Recipe
import dagger.hilt.android.HiltAndroidApp
import durdinapps.rxfirebase2.RxFirestore

@HiltAndroidApp
class VeganApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        setProducts()
//        setRecipes()
        val recips = createRecipes(createProductsRefrigerator())
//        setRecipes(recips.first)
//        setProductsrefrigerator(recips.second)
    }

    private fun setProductsrefrigerator(refrigerator: List<Product>) {
        refrigerator.forEach {
            RxFirestore.setDocument(
                Firebase.firestore.collection("refrigerator").document(it.id),
                it
            )
                .subscribe()
        }
    }

    private fun setRecipes(recipes: List<Recipe>) {
        recipes.forEach {
            RxFirestore.setDocument(
                Firebase.firestore.collection("recipes").document(it.id),
                it
            )
                .subscribe()
        }
    }

    fun createProductsRefrigerator() = listOf<Product>(
        Product(Firebase.firestore.collection("refrigerator").document().id, "Тофу"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Помидор"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Огурец"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Веген.сыр"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Нут"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Орехи"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Рис"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Гречка"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Макараноы"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Грибы"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Фасоль"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Мука"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Булгур"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Горох зеленый"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Соя"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Авокадо"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Банан"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Яблоко"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Мандарин"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Груша"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Киви"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Чечивица"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Овсяная крупа"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Кокосовое молоко"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Овсяное молоко"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Рисовое молоко"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Брокколи"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Шпинат"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Клубника"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Корица"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Картофель"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Чеснок"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Лук"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Батат"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "изюм"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Курага"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Финики"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Веган.колбаса"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Папарика"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Кешью"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Миндаль"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Фундук"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Кокосовая стружка"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Манго"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Руккола"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Виноград"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Нори"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Фунчоза"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Соевый соус"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Масло"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Уксус"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Чука"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Дайкон"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Морковь"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Семена чиа"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Лимон"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Темный шоколад"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Сельдерей"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Ваниль"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "сахар"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Агар-агар"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Темный шоколад"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Панировочные сухари"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Подсолнечное масло"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Каенский перец"),
        Product(
            Firebase.firestore.collection("refrigerator").document().id,
            "Консервированный нут"
        ),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Баклажан"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Оливковое масло"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Чеснок"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Винный уксус"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Сушеный чеснок"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Реджувелак"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Ржаная мука"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Горчичны порошок"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Листья вишни"),
        Product(
            Firebase.firestore.collection("refrigerator").document().id,
            "Перец черный (молотый)"
        ),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Крахмал"),
        Product(Firebase.firestore.collection("refrigerator").document().id, "Куркума"),
    )

    private fun createRecipes(refrigerator: List<Product>): Pair<List<Recipe>, List<Product>> {
        val actualRefrigerator: MutableList<Product> = refrigerator.toMutableList()
        val recipes = listOf(
            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Зефир на аквафабе",
                "2к",
                listOf(
                    "1) Чистим яблоки от кожуры и семечек, режем на маленькие кусочки. Кусочки складываем в сотейник, добавляем пару ст ложек воды и ставить на небольшой огонь. Периодически помешиваем. Яблоки нужно греть до тех пор, пока они не станут совсем мягкими и не выпарится влага. Готовое пюре пробиваем блендером и остужаем.",
                    "2) Берём 190 гр аквафабы (можно использовать из под консервированного горошка, нута), на маленьком огне увариваем до 80 гр. Остужаем.",
                    "3) Готовим сироп. В сотейник наливаем воду, добавляем 200гр сахара, 10гр агара. Ставим на медленный огонь и хорошо перемешиваем.",
                    "4) Пока варится сироп, взбиваем аквафабу, постепенно добавляя сахар, до пышной пены (самый важный шаг, в зависимости от мощности миксера может занять 10-25 мин)",
                    "5) В хорошо взбитую аквафабу добавляем яблочное пюре",
                    "6) Сироп доводим до 110 градусов (не менее 5-10 минут, с ложки сироп должен стекать непрерывной тонкой нитью)",
                    "7) Взбивая аквафабу, добавляем по стеночке струйкой горячий сироп, продолжая взбивать.",
                    "8) Взбиваем около 5 минут на высокой мощности",
                    "9) Помещаем смесь в кондитерский мешок с насадкой и отсаживаем зефир на коврик.",
                    "10) Посыпаем зефир немного сахарной пудрой и оставляем на сутки на стабилизацию при комнатной температуре",
                ),
                mapOf(
                    "Аквафаба" to "190 гр (80 гр вес уваренной аквафабы)",
                    "Сахар" to "50 гр",
                    "Яблочное пюре" to "~300 гр (100 гр вес уваренного пюре)",
                    "Сироп:" to "",
                    "Вода " to "75гр",
                    "Сахар " to "200гр",
                    "Агар-агар " to "10гр",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Простое овсяно-банановое печенье",
                "2к",
                listOf(
                    "1) Банан растереть вилкой или блендером в пюре",
                    "2) Добавить к пюре сахар, масло и овсянку, хорошо перемешать. Дать постоять и набухнуть минут 15",
                    "3) Добавить в массу орешки, шоколад или другие добавки",
                    "4) Ложкой выложить на пергамент будущие печеньки (используем силиконовый коврик или пергамент с силиконовым покрытием, чтобы было легче снять)",
                    "5) От толщины массы будет зависеть насколько будет хрустящим печенье",
                    "6) Ставим противень с пергаментом в разогретую до 170 градусов духовку (температура может меняться в зависимости от мощности духовки)",
                    "7) Выпекаем 20-35 минут, до золотистой корочки (Ориентируемся на цвет и свой вкус, чем дольше стоит, тем более хрустящим будет печенье)",
                ),
                mapOf(
                    "Банан крупный(мягкий)" to "1 шт",
                    "Овсяные хлопья" to "1 стакан",
                    "Кококсовое масло " to "1 ч/л",
                    "Ванильный сахар" to "1 ч/л",
                    "Можно добавить по вкусу:" to "",
                    "кусочки тёмного шоколада" to "",
                    "шоколадные капли" to "",
                    "мелкие орешки" to "",
                    "изюм, сушёные клюква и др ягоды," to "",
                    "кокосовая стружка" to "",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Овощные котлеты",
                "2к",
                listOf(
                    "1) Лук мелко нарезать и обжарить его на растительном масле до золотистого цвета.",
                    "2) Морковь и свеклу очистить и нарезать крупными кусками, затем пропустить их через мясорубку отдельно друг от друга либо натереть их на терке. Капусту мелко нашинковать.",
                    "3) Положить каждый овощ в отдельную сковороду, посолить, добавить к ним немного воды и тушить на медленном огне, помешивая, до полного размягчения.",
                    "4) Когда овощи размякнут, добавить к ним манку и мешать, пока манка не заварится. В итоге должен получиться плотный густой фарш трех видов. ",
                    "5) В каждый фарш добавить по трети жареного лука, перемешать. Сформовать котлеты и обвалять их в панировочных сухарях.",
                    "6) Обжарить котлеты на растительном масле с обеих сторон до золотистого цвета.",
                ),
                mapOf(
                    "Свекла" to "500 гр",
                    "Морковь" to "500 гр",
                    "Белокачанная капуста " to "500 гр",
                    "Репчатый лук" to "200 гр",
                    "Манная крупа" to "150 гр",
                    "Панировочные сухари" to "",
                    "Растительное масло " to "130 мл",
                    "Соль" to "по вкусу",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Парата с картофельной начинкой",
                "2к",
                listOf(
                    "1) Картофель очистить и отварить до готовности, затем размять вареную картошку в пюре.",
                    "2) Добавить чайную ложку соли, молотый чили, хлопья чили и измельченные кинзу и зеленый чили, перемешать.",
                    "3) Смешать просеянную муку, 300 мл воды и половину чайной ложки соли. Замесить мягкое, но не липкое тесто. Обернуть пленкой и оставить на 20–30 минут.",
                    "4) Разделить тесто на восемь одинаковых частей и скатать их в шары. Взять два шарика теста и раскатать их в круглые лепешки. Положить на одну лепешку около 3 столовых ложек картофельной начинки, накрыть второй лепешкой и хорошенько слепить края лепешек. Можно смочить края водой, чтобы легче было их склеить.",
                    "5) Медленно и не надавливая слишком сильно раскатать парату, убедившись, что начинка ни откуда не лезет наружу. ",
                    "6) Разогреть сковороду с топленым маслом и обжарить парату по 1–2 минуте с каждой стороны. Подавать с йогуртом и чатни.",
                ),
                mapOf(
                    "Картофель" to "5 шт",
                    "Кинза" to "50 гр",
                    "Перец" to "150 гр",
                    "Пшеничная мука" to "400 гр",
                    "Соль" to "1,5 ч/л",
                    "Масло" to "80 гр",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Жареный нут",
                "2к",
                listOf(
                    "1) Слить жидкость из банок с консервированным нутом, дать гороху обсохнуть. Смешать в небольшой миске паприку и кайенский перец.",
                    "2) Поставить большую сковороду на средний огонь, нагреть оливковое масло и обжарить в нем нут, можно в две партии, чтобы нут хорошо прожарился, до коричневого цвета.",
                    "3) Готовый нут вынуть шумовкой, переложить на бумажное полотенце, а затем, когда впитаются излишки масла, переложить горох в миску, посыпать смесью паприки и кайенского перца, посолить по вкусу, посыпать цедрой лайма, перемешать и тут же подавать.",
                ),
                mapOf(
                    "Оливковое масло" to "45 мл",
                    "Консервированный нут" to "500 гр",
                    "Цедра лайма" to "по вкусу",
                    "Копченая паприка" to "1 ч/л",
                    "Соль" to "по вкусу",
                    "Каенский перец" to "0,5 ч/л",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Нут с баклажаном",
                "2к",
                listOf(
                    "1) Нут замочить на ночь (или на 4–6 часов). После слить воду, отварить до готовности в свежей чуть подсоленной воде 60–90 минут. Аккуратно. Не переварите.",
                    "2) Баклажаны нарезать на крупные кубики, обжарить на оливковом масле со всех сторон до румяной корочки, посолить и тушить до мягкости/готовности. Остудить.",
                    "3) Кинзу, петрушку и чеснок измельчить, добавить паприку, зиру, оливковое масло, уксус и лимонный сок.",
                    "4) Нут, баклажаны и заправку смешать, посолить. Дать настояться в холодильнике часа 2.",
                ),
                mapOf(
                    "Нут" to "100 гр",
                    "Баклажан" to "2 шт",
                    "Оливковое масло " to "4 ст/л",
                    "Петрушка " to "1 пучок",
                    "Кинза" to "1 пучок",
                    "Чеснок" to "2 зубчика",
                    "Винный уксус" to "1 ст/л",
                    "Паприка" to "0,5 ч/л",
                    "Соль" to "по вкусу ",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Сыр из кешью",
                "2к",
                listOf(
                    "1) Замочить кешью на 10–12 часов в воде.",
                    "2) Слить из кешью воду. Соединить в чаше блендера все ингредиенты. Хорошенько их пробить, пока не получится масса, напоминающая крем-чиз. Убрать в холодильник на 12–18 часов.",
                ),
                mapOf(
                    "Кешью" to "400 гр",
                    "Сущеный чеснок " to "2 гр",
                    "Реджувелак" to "170 мл",
                    "Зеленый лук сушеный" to "2 гр",
                    "Соль" to "8 гр",
                )
            ),

            Recipe(
                Firebase.firestore.collection("recipes").document().id,
                imageUrl,
                "Жареный тофу в кляре",
                "2к",
                listOf(
                    "1) Для начала приготовьте маринад для сыра тофу. Этот шаг не обязателен, но если вы хотите получить насыщенный вкус, то смешайте соевый соус, пропущенный через пресс чеснок, сахар, и пол чайной ложки соли в отдельной емкости. Добавьте половину стакана воды и перемешайте.",
                    "2) Нарежьте сыр тофу на продолговатые брусочки. Подойдет также и адыгейский сыр, и сыр панир (в этом случае мариновать сыр не нужно). Поместите тофу в маринад на 20 минут, а лучше на ночь.",
                    "3) Разрежьте ножницами каждый лист водорослей нори на 4 равных квадрата. Подготовьте блюдце с водой — в нее вы будете макать нори, чтобы они не ломались при скручивании.",
                    "4) Увлажните один квадрат водорослей в воде, положите его на сухую доску и заверните в него трубочкой брусочек сыра. Так повторите со всем оставшимся сыром.",
                    "5) Приготовьте кляр для обжаривания нашей «рыбы»: смешайте муку, крахмал, добавьте щепотку соли, черного перца и пол чайной ложки куркумы. Разведите водой до состояния жидковатой сметаны.",
                    "6) Нагрейте в сковороде ложку растительного масла. Обмакните в кляр каждый завернутый брусочек и обжарьте на среднем огне со всех сторон до появления румяной корочки. Будьте внимательны: кляр схватывается очень быстро, главное не пережарить. Готово! Подавайте необычную «фальшивую рыбу» с рисом, картофелем, кашей, салатами или просто как закуску.",
                ),
                mapOf(
                    "Тофу" to "400 гр",
                    "Мука" to "3 ст/л",
                    "Чеснок" to "1 зуб.",
                    "Перец черный (молотый)" to "",
                    "Соль" to "0,5 ч/л",
                    "Подсолнечное масло" to "1 ст/л",
                    "Нори" to "4-5 листов",
                    "Крахмал" to "1 ч/л",
                    "Соевый соус" to "2 ст/л",
                    "Сахар" to "1 ч/л",
                    "куркума" to "0,5 ч/л",
                )
            ),
        )
        val trueRecipes = recipes
            .map { recipe ->
                val ids = recipe.ingredients
                    .map toIds@ { (name, value) ->
                        when (name) {
                            "Сироп:",
                            "Можно добавить по вкусу:" -> return@toIds name to value
                        }
                        var result: Pair<String, String>
                        try {
                            result = actualRefrigerator.first { it.name == name.trim() }.id to value
                        } catch (exc: Exception) {
                            val newId = Firebase.firestore.collection("refrigerator").document().id
                            actualRefrigerator += Product(newId, name)
                            result = newId to value
                        }
                        result
                    }
                    .toMap()
                recipe.copy(ingredients = ids)
            }
        return trueRecipes to actualRefrigerator
    }

    companion object {
        const val imageUrl =
            "https://static.1000.menu/img/content-v2/d3/e8/24736/oladi-ochen-pyshnye-na-kefire_1594324969_14_max.jpg"
    }
}