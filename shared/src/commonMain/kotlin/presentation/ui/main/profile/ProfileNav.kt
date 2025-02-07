package presentation.ui.main.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import presentation.navigation.ProfileNavigation
import presentation.ui.main.address.AddressScreen
import presentation.ui.main.address.view_model.AddressViewModel
import presentation.ui.main.edit_profile.EditProfileScreen
import presentation.ui.main.edit_profile.view_model.EditProfileViewModel
import presentation.ui.main.my_coupons.MyCouponsScreen
import presentation.ui.main.my_coupons.view_model.MyCouponsViewModel
import presentation.ui.main.my_orders.MyOrdersScreen
import presentation.ui.main.my_orders.view_model.MyOrdersViewModel
import presentation.ui.main.payment_method.PaymentMethodScreen
import presentation.ui.main.payment_method.view_model.PaymentMethodViewModel
import presentation.ui.main.profile.view_model.ProfileViewModel
import presentation.ui.main.settings.SettingsScreen
import presentation.ui.main.settings.view_model.SettingsViewModel

@Composable
fun ProfileNav(logout: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = ProfileNavigation.Profile,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<ProfileNavigation.Profile> {
            val viewModel: ProfileViewModel = koinInject()
            ProfileScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                errors = viewModel.errors,
                navigateToAddress = {
                    navigator.navigate(ProfileNavigation.Address)
                },
                navigateToEditProfile = {
                    navigator.navigate(ProfileNavigation.EditProfile)
                },
                navigateToPaymentMethod = {
                    navigator.navigate(ProfileNavigation.PaymentMethod)
                },
                navigateToMyOrders = {
                    navigator.navigate(ProfileNavigation.MyOrders)
                },
                navigateToMyCoupons = {
                    navigator.navigate(ProfileNavigation.MyCoupons)
                },
                navigateToMyWallet = {
                    navigator.navigate(ProfileNavigation.MyWallet)
                },
                navigateToSettings = {
                    navigator.navigate(ProfileNavigation.Settings)
                },
            )
        }
        composable<ProfileNavigation.Settings> {
            val viewModel: SettingsViewModel = koinInject()
            SettingsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                logout = logout,
                errors = viewModel.errors,
                action = viewModel.action,
                popup = {
                    navigator.popBackStack()
                },
            )
        }
        composable<ProfileNavigation.MyCoupons> {
            val viewModel: MyCouponsViewModel = koinInject()
            MyCouponsScreen(
                errors = viewModel.errors,
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable<ProfileNavigation.MyWallet> {
            /*val viewModel: MyWalletViewModel = koinInject()
            MyWalletScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }*/
        }
        composable<ProfileNavigation.MyOrders> {
            val viewModel: MyOrdersViewModel = koinInject()
            MyOrdersScreen(
                errors = viewModel.errors,
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable<ProfileNavigation.PaymentMethod> {
            val viewModel: PaymentMethodViewModel = koinInject()
            PaymentMethodScreen(
                errors = viewModel.errors,
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable<ProfileNavigation.EditProfile> {
            val viewModel: EditProfileViewModel = koinInject()
            EditProfileScreen(
                state = viewModel.state.value,
                errors = viewModel.errors,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable<ProfileNavigation.Address> {
            val viewModel: AddressViewModel = koinInject()
            AddressScreen(
                errors = viewModel.errors,
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
    }
}



