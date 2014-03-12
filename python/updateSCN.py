#!/usr/bin/python

####################################################

site = 'https://example.com:443'

SCNs = {'cl1.com': 'SCN-1024',
	'cl2.com': 'SCN-2043',
	'cl3.com': 'SCN-5367',
	'cl4.com': 'SCN-5367',
	'cl5.com': 'SCN-3165',
	'cl6.com': 'SCN-4421',
	'cl7.com': 'SCN-6215',
	'cl8.com': 'SCN-8126'
	}


####################################################

from jira.client import JIRA
import jira.exceptions
import getpass

uname = raw_input("Please enter support username: ")
pword = getpass.getpass("Please enter password for " + uname + ": ")

options = {'server': site}
auth = (uname, pword)
sup = JIRA(options, auth)



def updateSCNv2(tick, SCN_num):
    data = {'customfield_10101':
           {'name': SCN_num}
        }
    trans_id = getTransitionID(tick, 'Silently Edit')
    try:
        sup.transition_issue(tick, trans_id, data)
    except jira.exceptions.JIRAError, e:
        repr(e)

# the same transition name (e.g. Silently Edit) for an issue with one staus (e.g. Open) will not necessarily have the
# same transition id as that same issue when it it is in a different status (e.g. Investigating)
def getTransitionID(tick, transition_name):
    trans = sup.transitions(tick)
    for t in trans:
        if t['name'] == transition_name:
            return t['id']
    return None

jql = 'project=PTC and "Support Contract Number"=null'

try:
	nonSCNs = sup.search_issues(jql, 0, 100)
except jira.exceptions.JIRAError:
	print "Error due to one of the following:\n" \
	+ "- Incorrect login\n" \
	+ "- This user does not have access to the helpdesk project"
	exit(1)

print "\nFound {0} total tickets without assigned SCN values (the max this search will return is 100)".format(len(nonSCNs))
print "\nnow updating SCNs...\n"

updated_count = 0
updated_sucess = []
updated_fail = []

for ticket in nonSCNs:
	key = ticket.key
	email = ticket.fields.reporter.name.split('@')[1]
	if email in SCNs:
		print "Updating ticket {0} with {1}".format(key, SCNs[email])
		updateSCNv2(ticket, SCNs[email])
		updated_sucess.append("Ticket {0} updated with {1} (based on reporter email \"{2}\")".format(ticket.key, SCNs[email], email))
		updated_count += 1
	else:
		updated_fail.append("Ticket {0} with reporter email \"{1}\" could not be mapped to an SCN".format(key, email))

print "\nSuccessful:\n"

for i in updated_sucess:
	print i

print "\nFailed:\n"

for i in updated_fail:
	print i

print "\n\nA total of {0} tickets were updated with an SCN\n".format(updated_count)





############################################################################################

# below is alternate way of doing this update of SCNs directly through curl calls, however, as
# written, it does not use the Silently Edit transition, so notifications will be sent out


#def updateSCN(ticket_key, SCN_num):

#	update_headers = {"Authorization":"Basic base64encoded-uname:pword", "Content-Type":"application/json"}
#	jira_api_put = jira_site + "/rest/api/2/issue/"
#	update = {"fields": {"customfield_10101": {"name":SCN_num}}}
#	update_json = json.dumps(update)
#	update_url = jira_api_put + ticket_key
#

#	update_call = """curl -D- -v SSLv3 -H "Authorization: Basic base64encoded-uname:pword" -X PUT --data "{0}" -H "Content-Type: application/json" {1}""".format(update_json.replace("\"", "\\\""), update_url)
#
#	subprocess.call(update_call, shell=True)
#
#
#jira_api_get = jira_site + "/rest/api/2/search?jql={0}&fields={1}"

#get_headers = {"Authorization":"Basic base64encoded-uname:pword"}

#
#since_date = date.today() - timedelta(days=days_ago)
#since_date = str(since_date).replace("-","/")
#
#get_nonSCNs_JQL = "project=PTC and cf[10101]=null".format(since_date)
#get_nonSCNs_JQL = get_nonSCNs_JQL.replace(" ", "+")
#get_nonSCNs_fields = "key,reporter,cf[10101]"
#
#get_nonSCNs_url = jira_api_get.format(get_nonSCNs_JQL, get_nonSCNs_fields)
#
#get_req = urllib2.Request(get_nonSCNs_url, headers=get_headers)
#get_res = json.loads(urllib2.urlopen(get_req).read())
#
#print "\nFound {0} total tickets without assigned SCN values (the max this search will return is 50)".format(get_res["total"])
#print "\nnow updating SCNs..."
#
#updated_count = 0
#updated_sucess = []
#updated_fail = []
#
#for ticket in get_res["issues"]:
#	key = ticket["key"]
#	email = ticket["fields"]["reporter"]["emailAddress"].split("@")[1]
#	if email in SCNs:
#		print "Updating ticket {0} with {1} ({2})".format(key, SCNs[email], email)
#		updateSCN(key, SCNs[email])
#		updated_sucess.append("Ticket {0} updated with {1} ({2})").format(key, SCNs[email], email)
#		updated_count += 1
#	else:
#		updated_fail.append("Ticket {0} with reporter email \"{1}\" could not be mapped to an SCN".format(key, email))
#
#print "\nSuccessful:\n"
#
#for i in updated_sucess:
#	print i
#
#print "\nFailed:\n"
#
#for i in updated_fail:
#	print i
#
#print "\nA total of {0} tickets were updated with an SCN\n".format(updated_count)
