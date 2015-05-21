#!/usr/bin/python -u

import sys
import subprocess
import json
from time import sleep
import getpass
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException

# check if root
running_user = subprocess.Popen("whoami", stdout=subprocess.PIPE).stdout.read()
if "root" != running_user[0:4]:
	print "current user is " + running_user
	print "must be root to run this script"
	sys.exit(1)


profile = webdriver.FirefoxProfile()
#profile.set_preference("network.proxy.type", 2)
#profile.set_preference('browser.download.folderList', 2) # custom location
profile.set_preference('browser.download.manager.showWhenStarting', False)


wd = webdriver.Firefox(firefox_profile=profile)
wd.set_window_size(1920, 1080)
#wd.maximize_window()


class ConfigVC:

    vc_url = 'https://localhost:5450/webui'
    user = 'uidbadmin'
    pwd = 'uidbadmin'
    group = 'verticadba'
    def_path = '/home/uidbadmin/vertica_dbs'
    db_ip = ['127', '0', '0', '1']
    db_name = 'testdb1'


    def setup(self):
	
	print 'performing initial setup of ' + self.vc_url + '...'
        # screen 1
        wd.get(self.vc_url)
	sleep(20)
        agree = wd.find_element_by_id('agree')
        agree.click()
        next = wd.find_element_by_id('carousel-next')
        next.click()

        # screen 2
        sleep(3)
	unixUid = wd.find_element_by_id('unixUid')
	unixUid.send_keys(Keys.CONTROL + "a")
	unixUid.send_keys(self.user)
        unixPwd = wd.find_element_by_id('unixPwd')
        unixPwd.send_keys(self.pwd)
        unixPwdConfirm = wd.find_element_by_id('unixPwdConfirm')
        unixPwdConfirm.send_keys(self.pwd)
        groupId = wd.find_element_by_id('groupId')
        groupId.send_keys(self.group)
        next.click()

        # screen 3
        sleep(3)
        catalog = wd.find_element_by_id('verticaDefaultCatPath')
        catalog.send_keys(Keys.CONTROL + "a")
        catalog.send_keys(self.def_path)
        data = wd.find_element_by_id('verticaDefaultDataPath')
        data.send_keys(Keys.CONTROL + "a")
        data.send_keys(self.def_path)
        temp = wd.find_element_by_id('verticaTempPath')
        temp.send_keys(Keys.CONTROL + "a")
        temp.send_keys(self.def_path)
        next.click()

        # screen 4
        sleep(3)
        finish = wd.find_element_by_id('wizard-finish')
        finish.click()

        sleep(120)

    def login(self):

        # login screen
	print 'logging in to ' + self.vc_url + '...'
	wd.get(self.vc_url)
	
	try:
		WebDriverWait(wd, 15).until(EC.alert_is_present(),
                                   'Timed out waiting for PA creation ' +
                                   'confirmation popup to appear.')

		alert = wd.switch_to.alert
		alert.accept()
	except TimeoutException:
		pass
	uname = wd.find_element_by_id('j_username')
        uname.send_keys(Keys.CONTROL + "a")
        uname.send_keys(self.user)
        passw = wd.find_element_by_id('j_password')
        passw.send_keys(Keys.CONTROL + "a")
        passw.send_keys(self.pwd)
        login = wd.find_element_by_id('login-btn')
        login.click()

    def add_db(self):

        # go to manage screen
        print 'adding db ' + self.db_name + ' running on host ' + '.'.join(self.db_ip) + '...'
	sleep(3)
	wd.get(self.vc_url + '/databases')
        sleep(3)
        try:
            close = wd.find_element_by_id('closeHelpBtn')
            close.click()
        except:
            pass
        manage = wd.find_element_by_id('manageSection')
        hov = ActionChains(wd).move_to_element(manage)
        hov.perform()
        import_db = wd.find_element_by_id('manageNewBtn')
        import_db.click()
        sleep(2)
        for i in range(0, 4):
            ip_field = wd.find_element_by_id('ip' + str(i + 1))
            ip_field.send_keys(self.db_ip[i])
        next = wd.find_element_by_id('continueDiscCluserBtn')
        next.click()
        sleep(10)
        apikey = wd.find_element_by_id('apikey')
        apikey.send_keys(json.load(open('/opt/vertica/config/apikeys.dat'))[0]['apikey'])
        next = wd.find_element_by_id('doAPIContinueBtn')
        next.click()
        sleep(30)
        f_uname = wd.find_element_by_name('uname_' + self.db_name)
        f_pass = wd.find_element_by_name('pwd_' + self.db_name)
        f_uname.send_keys(self.user)
        f_pass.send_keys(self.pwd)
        next = wd.find_element_by_id('doImportBtn')
        next.click()
        sleep(10)
        #next = wd.find_element_by_id('finishManageBtn')
        #next.click()



config = ConfigVC()

for i in range(1, len(sys.argv)):
    method = getattr(config, sys.argv[i])
    method()

print 'finished VC config'

sleep(5)

wd.quit()
