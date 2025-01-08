package com.singlepointsol.carinsurance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.navigation.RootNavGraph
import com.singlepointsol.carinsurance.ui.theme.CarInsuranceTheme
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            CarInsuranceTheme {


                val customerViewModel = viewModel<CustomerViewModel>(this)
                val vehicleViewModel = viewModel<VehicleViewModel>(this)
                val productViewModel = viewModel<ProductViewModel>(this)
                val productAddonViewModel = viewModel<ProductAddonViewModel>(this)
                val agentViewModel = viewModel<AgentViewModel>(this)
                val proposalViewModel = viewModel<ProposalViewModel>(this)
                val policyViewModel = viewModel<PolicyViewModel>(this)
                val policyAddonViewModel = viewModel<PolicyAddonViewModel>(this)
                val claimViewModel = viewModel<ClaimViewModel>(this)
                val customerQueryViewModel = viewModel<CustomerQueryViewModel>(this)
                val queryResponseViewModel = viewModel<QueryResponseViewModel>(this)
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides this
                ) {
                    RootNavGraph()
                }
            }
        }
    }
}
