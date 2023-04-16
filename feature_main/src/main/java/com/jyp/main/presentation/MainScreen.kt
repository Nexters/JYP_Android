package com.jyp.main.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.jyp.jyp_design.resource.JypColors


@Composable
internal fun MainScreen(
    screens: List<MainScreenItem>,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BottomNavItem.fromRoute(
        backStackEntry?.destination
            ?.route
            ?: BottomNavItem.MY_JOURNEY.name
    )

    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                navItems = BottomNavItem.values().toList(),
                currentScreen = currentScreen,
            ) { navItemName ->
                if (currentScreen.name == navItemName) {
                    return@MainBottomNavigation
                }

                navController.navigate(navItemName) {
                    popUpTo(BottomNavItem.MY_JOURNEY.name) {
                        if (navItemName == BottomNavItem.MY_JOURNEY.name) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            screens = screens,
        )
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screens: List<MainScreenItem>,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MY_JOURNEY.name,
        modifier = modifier
    ) {
        screens.forEach { screen ->
            composable(screen.navItem.name) {
                screen.content.invoke()
            }
        }
    }
}

@Composable
internal fun MainBottomNavigation(
    navItems: List<BottomNavItem>,
    currentScreen: BottomNavItem,
    onTabSelectAction: (String) -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x1D000000))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            navItems.forEach { navItem ->
                MainBottomNavigationItem(
                    selected = navItem == currentScreen,
                    activatedIconRes = navItem.activatedIconRes,
                    inactivatedIconRes = navItem.inactivatedIconRes,
                    labelRes = navItem.labelRes,
                    onTabSelectAction = {
                        onTabSelectAction.invoke(navItem.name)
                    }
                )
            }
        }
    }
}

@Composable
internal fun RowScope.MainBottomNavigationItem(
    selected: Boolean,
    @DrawableRes activatedIconRes: Int,
    @DrawableRes inactivatedIconRes: Int,
    @StringRes labelRes: Int,
    onTabSelectAction: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(JypColors.Background_white100)
            .clickable(
                onClick = onTabSelectAction,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(
                id = when (selected) {
                    true -> activatedIconRes
                    false -> inactivatedIconRes
                }
            ),
            contentDescription = stringResource(id = labelRes)
        )
    }
}

@Composable
@Preview
internal fun MainScreenPreview() {
    MainScreen(
        listOf(
            MainScreenItem(
                navItem = BottomNavItem.MY_JOURNEY,
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Hello World!")
                    }
                }
            )
        )
    )
}