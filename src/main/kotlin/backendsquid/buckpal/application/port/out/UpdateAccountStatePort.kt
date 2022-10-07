package backendsquid.buckpal.application.port.out

import backendsquid.buckpal.account.domain.Account

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}