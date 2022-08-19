package com.jyp.feature_planner.presentation.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlannerActivity : ComponentActivity() {
    private val viewModel: PlannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    viewModel = viewModel,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(viewModel: PlannerViewModel) {
    val pikMes by viewModel.pikMes.collectAsState()
    val tags by viewModel.tags.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedTag by remember {
        mutableStateOf(tags.firstOrNull())
    }

    BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                selectedTag?.let {
                    TagSelectedBottomSheetScreen(tag = it)
                }
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Box(
                modifier = Modifier.fillMaxSize()
        ) {
            PlannerScreen(
                    pikMes = pikMes,
                    joinMembers = listOf(
                            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUSEBIVFRUVFRUQFRcVFRUVFRUVFRUXFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMsNygtLisBCgoKBQUFDgUFDisZExkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIALEBHAMBIgACEQEDEQH/xAAbAAABBQEBAAAAAAAAAAAAAAABAAIDBAUGB//EAD8QAAEDAgQDBgUCAwUJAQAAAAEAAhEDBAUSITFBUWEGInGBkaETMrHB8ELRFCNyUmKisuEVMzSCg5LC0vEH/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/EABQRAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQA/AOPTmpsotQSBGU0IoHJ7UwJwQOVavfMHdza8eg46lWFgXls6m5wY3PnOadczRvlniDB2QHFrun3QGmB3tIAI4wZ133XPV6gJlu3DYHwMK/d1t2uY3XXuyB5cj1WW4ckDzqokWlIhAkWvhJrSl8MoL9jiZYTILp1jYSk7EnlxJOp38OQ5BUmwN051SdJCDpcJxppIBcG8IIyj1krqKdQESF5fC6fAbx1LKHGaTzDTyP2QdakgCkSgKBSQKApSmlJA6UpTUkBJSJTSiAgJKSCSBFRlSlRFAilKQCKAIIoIMwBFAJwCBwRQCegQTgEgigICy8SpveX5XQKbdI0LnETHTSPVaoQbTgkjjE+SDibpvHU9SZPmVWoW7nmGgknTQLqsWwgOkt0JIgRoSeC9Q7JdmqdvRYwNBfAL3Hcu4oPGKPZq4Jy/DdPguksOwFRzZdpzle1iyEbBUr2nl0AQeWHsU1gkkFZl3gAGwGi9KvmaLksVqRKDgMQw8tmOCzXN5rsK1KdSucvqEE8kFFrlrYXWzUqlI8B8RvQjl7LIcFdwcE1YHWfDc+yD0K0fmY082tPsFIU2hEabcPBSIAkUYRhAITYT5TZQJFBGUAISCRSQCEUk0lA8KMhOCaSgUIQiEkAhAtTpTSgywnhCE8BAgnhBoT4QIBEBIBOAQJEIwlCCfDKWe5oMO2f4h/6bS4e4avWcOpheX9mwP4ukTxD2j/tJP0XqtkNEE7gs/EGStA/krke3XaQW7MlKHVD7IKuM3tOk0l7gOnFcLf4myo7ulHDsIuL0mrUJyjiZ16BQ4lhDKZjkgjIkLKxK2HmtKmIGir1xKDlqrFp9l6EvcYmBl66qtWpS/KOa1cNomhL2iSYnwQdXQGgnkApUym+QDzEooHFAlGU0oCmoyggSKGZDMgcUClmQJQFBDMlKAgoIFBAUpQQQOKCSEoKEpzVGAnhBI0hEFMRQSApwKjCcEEgKRcmIEoJ7So9r2upCXtMt8Yj7rrbbEcUYM7qTcnQtmPVcbQrlj2vG7SHehmF1l/XxG5aW2wpUaYcBmrS51RunfaG6NG8CCTpsg1sMxp9Z+UA5oOi5DHaDqt58KoCI1dx0GpXa9mMKdQp56tQPqEHvBpYI8CT9eCwbe6aLtzzEnTXj0QV77E7w2zv9n2bBTaAGmq4tc9uxNOmIkdSdeRXEPtLxxzV2tbOu/wBpP1Xs76ZqN7rnAHgD/oswdmqebPUlxGupJQcJZYS408zlj3TYJC9B7Q1Q1uVojRee3Z1KCi237+ZW2FwdBHdMBNYVYaZ9vwoNi2ENA5BSrBt8fZBaWklpI0jUA7iU+j2loOMHM09R+yDalAlRUrhrhLSCOiOdA+UgmGolnQPKCYXpudBIkoi4ofEQTFCVFnQzIJZSlRZkcyCWUCVHnSzoHgoyos6WcoK2VEBTBieGIIQEQ1ThgTg1BAAiGqcNRyoIcqaQp8qd8NBTc0r1rCbcOo0jOnw2H/CF5flXoXZO9DrVuurJpn/l2/w5UGtiVRrKTiTtp7LymtUa6s5xfAEkdTyXe4nhtS4Hdq5BmhzYkObInXgd1zGNf/nAnOyrOsjMNR4FBL2Q7TfzTQqGQ75SeHQ9F291Vhq87s8CbbHO8lx5la912gzM08EGb2nvNwFxdZ0lamKVXVHFUHU0FZ5gKqMZIBaG7aTwU146GnoFiD88UApv7x/OCttphw135/uqIG3WVZpvgoLll8RjpbPkuioXRI1Co2VqXAELYtaUbe6BNMp2VThg4gHw0+ijc9vUeKCMhIIz+cEkAJTSnFCEAhKE4BJAIShGEkDYShOSQNhJFJA8BOAToTsqBmVOARanQgYAjqngIoIwE4pwCDkDC1XsCxA0qmX9L4B6Hg4ffp4LPK0sPtYaHEfNJ8phB2X8Y5g0YXacNY8gql/idw8RRoOjcl0NA6nMnYRcBzcpOsR5bfnkrr7GpU0+KWt5Ab+e6DgscZeOEF9MbaAFx+yitLBwphtSpmdMk5QNDw3XYYrhcfKuYuaZaSSUGViDAw6brKuKys3RL3hrdSTA81nY/bVKA7wmdPAnmgzcSuNI5rOCDyTukSgAMu8Ap7TU+ar0eJU+HiSPFB2+Bs0W18AR191UwelDQtLMOSClXpwNVRq6anyiZJPILTuDAk84A5n9uPkVlXkk9Bvwmfsgo17p2seg28+f0UtncZhB3UN230VW2qwUG0kmtKcgSKCSAoJJIEiklKAJIpIJ4TkYTgEDITgE+EEDYRRTSUBlNIShHKgYQt67pltNuXdoAj0/PNY9BkuaObgPdbOK1Mve4fK4dCgoU77L32mJ1/pPCf2Xe29+3IHcwCvL8QPwznEGm6J8we947+i6bBMPq1qDXMqd13sASJ+miC5j+OsAMRyXn2L45OgPivRqXYmgTNZznnlmIHssjE+xts45SxjTInJ8pE8QNp2QYfYvs7WqP/iazCymBmYXaF5P6gN468ZWf2/xBriWCI2Xb9qO0eSiWU2w7RpbPyjgRzHIrxvF67nPObggoJripm27yC4NJA3IBhRBkoEzRp9VYw8wW+KhqbJ1F2oQelYa/ug9FepGTrpxXO4TdTSaObo9gtS7uslJzhwHudB9fZBFWrF588p/9R+anySqs90qNHK1s76u9UHvnyQZ143ksgmCtq71WLcboNmxqy3qFZCxcPrQrxrO5oLqBVB1Q8ykKp5oNBKVn/HdzR/iXIL0oqk27PIIi85j0QXUVVbdt5wpBVbzHqEGkAiGpwCKBqBT4SQMKGVOKBQCECEYSQSWY/mM/qb9VsXzxsdjoVjUHQ5p5EHylaFasHAj9TTlcOR5+BGqDBq1PhuNGof5b5DHHZriNj0W32Ksb1lOsGuDaOc/CLpJJmHZR/ZgDzPisbE6bXsLH+R5LtewOJ57RtF/eqUZpu6tkmm/zHHiWlBF2VpXguaovqgLMg+CB8ri4nMectAGn97orWO4WYLgSCNiCZHmpL3FKROVxLHA90u016O2VplwSwCoZB0D+B/q5H2Qea3dF1V2SoSHj5XH7rExBtNmZlenLh8rmkeh5jqF6P2hwYhoLGlzye7z6LhMWYTIqNh7dCD00KDMw+8DWkcCICr3VkJgBS0bZrXA8N+krRptzGTx/N0HJXtPLom0QreNshxVbDhJgoN/Ba+kcnT6iFpX1TM+nT4ZpPkJWDbkscpjfwXVCZiWt8XaaIN+7ve9lHDRLNoszDqUd5+rnaxyHJXi5AK7lj3JWlVP7LKuSgfaO1V+k7RZdudVdpu3HIoLBemFyhL001EExckXquaiGdBMXoZ1AZRQSko5lBmSzoO2RARhAoEhCKdKCMhCE+UHIGQkimoEVeuaAeM7TDxpIO4OrZGxCoFaFtbl1MOBhwJA8NNOomUEWF4LVus/eDBTiXRJJM6BvlzXT9msOFrTfSc4EvfnDtgdAA3pqD6rDwjHhbFzawhjiHSB8pAg+I28Fu32KZrc17drH90vZ8XMKbgATqRxMQBzhA/F8Oa9pzCZ3WVgdy+3JZU71E8TqWePMe6Z2Txx9d9Zj3ZmtPckd4A8Mw3HkVtXlu1wDSJ3kcYJk5eaDLve1NOjUa1xmn+l7e9lnQggbtj0XL9sMVtqj2mg7MeJAI06yFo9q8CpMpuqM7sSdzHgVxdPK7ogTv8A4rNrU7oPKR5qL4ekHcbfso6cifzVBjY4O+qFnUyvaeRV/FxqswboOjuqPe02I+yyGv74aeBzLYpOzUWu4t0WNdjK8O4H7oN+0r6abq413sufoVtYGw3Wgy4zQ1vgguEzJ4BZ9Vsgu4D3KtX9QANpM1PGOJ5KLGRka2mfmiSOSCjROsq2KwBkmOCp0xASqaz5H7fdBYdUHA6ISocp0UgKAkFEJuZEFA4lCU10oNCCRABKUpQd1KUJ0IIFCCcUEDUQkUigY4IFOIQIQNhbFsz+S2HRudt+8VkrRrueGhjGEw2M0gaxrug6irh9G7oiDlHDLALY4K5h1g2nRbRJzBoyyQO8OoGkrjsIu6lIF0xJ20IMDSYHj1Vs9qSCGvaN9TroDsQBv+b7ILN/2dbQeLi2y04nO0NGV4P9oc+qxe0mImq0BjXAt70tOo6iNVexPtCHNyPOh2c3vNPg4fssmjfODTlh4JJBbr5f6IMuu59doFSs5w5E8efVVKuF5dk51J8klpEmfVaFvUcdCgyatuRGbxBUb6UiePFbd1SLgGx4Jv8ABEDVByuJ2Bc3MPFc/Xolp1XoVza90lo4Lk8RaM7hwGnTTRBBhlzHdOx0Tbpu4OyhNvr3T6qRziNHjzQQMJEq9bXIYJ/UdAFULUKTyD3QAeca+qDdtajaLTVq61D8reXU9VWtaD7ioXu23JPAKK2tMxzVXacyrlxfFzfhUB3eLv8AVBVu6gL4bsNAlQYTmPABOdb5Brur3wxTpNBHeeS4+HD2QVsktjiNv2VYq00xz8lHXj5hx38UFeU8IFyEoJM6WZRyiCgfKWZEBODAg7xOQShAEiiggBQRKUIGkJpTyUCEDCtW/wA3yh0CI01cT0CzWjiIkbTtPXoN/JaFpbZdXEucYLi7cnkBsAOQQNMNZEyWgAzvPHbxWRXEknx67/m3HgpL64yXBa4wyqMng79P7eadiAgxHl4/h9OaDBxC0D9DpxkEg8gZ47aT4FYhta9u4vtqxB4jnB4jZ0dQty7uPvx5/nn4rDvrqTug6jDO1zKgDLqnkqaDM2Sx3kNWn26rYZUYSMpC84s7oNcXcQIHQnj6KO4vXEzJHhog9coMGhKtPwsv2EBeR2GOXNIF4qktHda13eBcfHWANT5Diu17J9vnVHClVY0OOjYcQHdBOx6FBdx2l8JpzaAd6eYH57rzWq+SSeJJXZ9ssW+KDTYeMuj/ACyuJc1BNRGqvm1D6buQE+aoURqulZRyUI/U/wAjBGvsg40CN/FNFQ8HK04QSOphVzT1I0nfxQT0SCZc6ekrYtXiIaPRYDQRwHoFet7vg6QOm3sg0brI35zmO4aOf948uiqvrue7M7f6dApqVuw6tcHJlalB5IHCmZkE+yrVTBg/h/CrtE6KriOhB5/ZBEkSo2ulPCBSiwpQiAglBSlMCBKD0FOCCSBwTXJJIAgkkgSaUkkEtD/yZ/mWoPz3SSQcn2y/T/UtTFP0/wBJ+ySSDjr/AG9fqsWvv5pJIIG8fFIpJIH3H+7p/wBVX6U1b7Nf8Q3wf/kckkg0qu/5zVG4380UkBs9x4j6FdTivyt8/oUkkHGXXzHxTT83kkkgTdvT6KW3+Y+aCSCep8wV+r8nkikgZb7KDEOHn9kkkFVicUUkATkkkBCaUUkH/9k=",
                            "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/2TYFQJEPNQFGGM6T5FLBXFHHZ4.jpg",
                            "https://img.khan.co.kr/news/2019/06/26/l_2019062601003100200243781.jpg",
                            "https://ojsfile.ohmynews.com/STD_IMG_FILE/2017/1206/IE002255004_STD.jpg",
                    ),
                    tags = tags,
                    tagClick = {
                        selectedTag = it
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
            )
            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(JypColors.Black20)
                )
            }
        }
    }
}
