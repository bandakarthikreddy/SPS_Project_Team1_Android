package com.singlepointsol.carinsurance.screens.welcomescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.DrawerContent
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.formsscreen.FormsScreen
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.AgentPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.ClaimPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.CustomerPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.CustomerQueryPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.PolicyAddOnPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.PolicyPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.ProductAddOnPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.ProductPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.ProposalPage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.QueryResponsePage
import com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages.VehiclePage
import com.singlepointsol.carinsurance.screens.welcomescreen.otherpages.ContactUsPage
import com.singlepointsol.carinsurance.viewmodel.AgentViewModel
import com.singlepointsol.carinsurance.viewmodel.ClaimViewModel
import com.singlepointsol.carinsurance.viewmodel.CustomerQueryViewModel
import com.singlepointsol.carinsurance.viewmodel.CustomerViewModel
import com.singlepointsol.carinsurance.viewmodel.PolicyAddonViewModel
import com.singlepointsol.carinsurance.viewmodel.PolicyViewModel
import com.singlepointsol.carinsurance.viewmodel.ProductAddonViewModel
import com.singlepointsol.carinsurance.viewmodel.ProductViewModel
import com.singlepointsol.carinsurance.viewmodel.ProposalViewModel
import com.singlepointsol.carinsurance.viewmodel.QueryResponseViewModel
import com.singlepointsol.carinsurance.viewmodel.VehicleViewModel
import kotlinx.coroutines.launch


@Composable
fun WelcomeScreen(modifier: Modifier, navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(drawerState = drawerState, navController = navController)
        }
    ) {
        // NavHost with Scaffold only for the home screen
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                // Scaffold with TopAppBar for home screen
                Scaffold(
                    topBar = {
                        TopAppBar(
                            onOpenDrawer = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }
                        )
                    },
                    content = { padding ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            ScreenContent(modifier = Modifier.padding(padding))
                        }
                    }
                )
            }

            composable("contactus") {
                ContactUsPage(modifier = Modifier.padding())
            }

            composable("forms") {
                FormsScreen(modifier = Modifier, navController = navController)
            }

            composable("customer") {
                CustomerPage(
                    modifier = Modifier, CustomerViewModel())
            }

            composable("vehicle") {
                VehiclePage(modifier = Modifier, VehicleViewModel())
            }

            composable("product") {
                ProductPage(modifier = Modifier, ProductViewModel())
            }

            composable("productAddon") {
              ProductAddOnPage(modifier = Modifier, ProductAddonViewModel())
            }
            composable("agent") {
                AgentPage(modifier = Modifier, AgentViewModel())
            }
            composable("proposal") {
                ProposalPage(modifier = Modifier, ProposalViewModel())
            }

            composable("policy") {
                PolicyPage(modifier = Modifier, PolicyViewModel())
            }

            composable("policyAddon") {
                PolicyAddOnPage(modifier = Modifier, PolicyAddonViewModel())
            }

            composable("claims") {
                ClaimPage(modifier = Modifier, ClaimViewModel())
            }

            composable("customerQuery") {
                CustomerQueryPage(modifier = Modifier, CustomerQueryViewModel())
            }

            composable("queryResponse") {
                QueryResponsePage(modifier = Modifier, QueryResponseViewModel())
            }
        }
    }
}