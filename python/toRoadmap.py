#!/usr/bin/python

########################################################

site = 'https://example.com:443'
link_type = 'Roadmap'
# comp = 'Business Administration Support'

########################################################

from jira.client import JIRA
import jira.exceptions
import getpass

def getTransitionID(tick, transition_name):
    trans = sup.transitions(tick)
    for t in trans:
        if t['name'] == transition_name:
            return t['id']
    return None

def getResolutionID(resolution_name):
    res = sup.resolutions()
    for r in res:
        if r.name == resolution_name:
            return r.id
    return None

# connect to support site
options = {'server': site}

uname = raw_input("Please enter support username: ")
pword = getpass.getpass("Please enter password for " + uname + ": ")

auth = (uname, pword)
sup = JIRA(options, auth)

t1 = raw_input("Please enter key of service desk ticket to transition to roadmap: ")
proj = raw_input("Please enter key of roadmap project to transition " + t1 + " into: ")
print

try:
	t1 = sup.issue(t1)
	proj_test = sup.project(proj)
except jira.exceptions.JIRAError:
	print "Error due to one of the following:\n" \
	+ "- Incorrect login\n" \
	+ "- This service desk ticket or roadmap project does not exist\n" \
	+ "- This user does not have access to this service desk ticket or roadmap project"
	exit(1)

t2_summary = t1.fields.summary
t2_description = t1.fields.description
t2_reporter = t1.fields.reporter.name
t2_issuetype = t1.fields.issuetype.name
t2_priority = t1.fields.priority.id

if t2_description is None:
    t2_description = ''


t2_fields = {"project": {'key': proj},
                 'summary': t2_summary,
                 'description': t2_description,
                 'reporter': {'name': t2_reporter},
                 'issuetype': {'name': t2_issuetype},
                 'priority': {'id': t2_priority}
            }

print "Creating ticket in project {0} with summary, description, issue type, priority, and reporter from {1}...".format(proj, t1.key),
t2 = sup.create_issue(t2_fields)
print "success (ticket {0} created)".format(t2.key)

print "Adding watchers from {0} to {1}...".format(t1.key, t2.key),
for w in sup.watchers(t1).watchers:
	sup.add_watcher(t2, w.name)
print "success"

print "Linking {0} to {1} with link type of {2}...".format(t1.key, t2.key, link_type),
sup.create_issue_link(link_type, t1.key, t2.key)
print "success"

print "Adding closing comment to {0}...".format(t1.key),
t1_comment = 'Closing this ticket, this issue can be further communicated in linked roadmap ticket {0}.\n\nThanks'.format(t2.key)
sup.add_comment(t1.key, t1_comment)
print "success"

print "Silently closing {0}...".format(t1.key),
sup.transition_issue(t1, getTransitionID(t1, 'Silently Close'), resolution={'id': getResolutionID('Transitioned to Roadmap')})
print "success"

print "Adding comment to {0} directing to refer to linked service desk ticket {1} for previous communication...".format(t2.key, t1.key),
t2_comment = 'Please refer to linked Service Desk ticket {0} for previous communication on this issue.'.format(t1.key)
sup.add_comment(t2, t2_comment)
print "success"


# below prints transition types available
#trans = sup.transitions(t1)
#[(t['id'], t['name']) for t in trans]
#for i in trans:
#    print i['id']
#    print i['name']


# add BA component (from online forums)
#issueKey='PROJECT-001'
#headers={'Content-Type': 'application/json'}
#
#params = '{"update" : {"components" : [{"add" : {"name" : "%s"}}]}}' % component
#restUrl = '%s/rest/api/2/issue/%s' % (url, issueKey)
#response = requests.put(restUrl, data=params, auth=auth, headers=headers)

#a = sup.issue_link_types()
#for i in a:
#    print i.id

sup.kill_session()

print "Completed, closing session... done"
print
