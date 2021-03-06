package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.model.beans.Account
import com.jscoolstar.accountremeber.model.beans.Cate
import com.jscoolstar.accountremeber.model.beans.ExtraColumn
import com.jscoolstar.accountremeber.model.beans.User
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.db.entity.DMCate
import com.jscoolstar.accountremeber.db.entity.DMExtraColumn
import com.jscoolstar.accountremeber.db.entity.DMUser

fun DMExtraColumn.toExtraColumn(): ExtraColumn {
    var ex = ExtraColumn()
    ex.id = id
    ex.aId = aId
    ex.key = key
    ex.value = value
    return ex
}

fun ExtraColumn.toDMExtraColumn(): DMExtraColumn {
    var dmColumn = DMExtraColumn()
    dmColumn.id = id
    dmColumn.aId = aId
    dmColumn.key = key
    dmColumn.value = value
    return dmColumn
}

fun Cate.toDMCate(): DMCate {
    var dmCate = DMCate()
    dmCate.id = id
    dmCate.cateName = cateName
    dmCate.userId = userId
    return dmCate
}

fun DMCate.toCate(): Cate {
    var cate = Cate()
    cate.id = id
    cate.cateName = cateName
    cate.userId = userId
    return cate
}

fun DMAccount.toAccount(): Account {
    var account = Account()
    account.id = id
    account.accountName = accountName
    account.bindmail = bindmail
    account.bindphone = bindphone
    account.platform = platform
    account.tip = tip
    account.password = password
    account.create_time = create_time
    account.userId = userId
    return account
}

fun Account.toDMAccount(): DMAccount {
    var account = DMAccount()
    account.id = id
    account.accountName = accountName
    account.bindmail = bindmail
    account.bindphone = bindphone
    account.platform = platform
    account.tip = tip
    account.password = password
    account.create_time = create_time
    account.userId = userId
    account.cateId = if (cate != null) cate!!.id else 0
    return account
}

fun User.toDMUser(): DMUser {
    var user = DMUser()
    user.userId = userId
    user.nickName = nickName
    user.passwordTip = passwordTip
    return user
}

fun DMUser.toUser(): User {
    var user = User()
    user.userId = userId
    user.nickName = nickName
    user.passwordTip = passwordTip
    return user
}