import icalendar

f = open('/home/ssakhuja/Downloads/calendar.ics', 'r')
cal = icalendar.Calendar.from_ical(f.read())


for comp in cal.walk():
	if comp.name == 'VEVENT':
		print comp['attendee']
		print comp['dtstart'].dt
		print comp['dtend'].dt
		print comp['dtstamp'].dt
f.close()

<<<<<<< HEAD
# use below command to get icalendar from confluence:

# curl -G -k https://username:password@wiki.hq.boston-engineering.com/rest/calendar-services/1.0/calendar/export/subcalendar/b46402cc-44c8-42da-866b-39881d278506.ics?os_authType=basic
=======
# use below command to get icalendar from URL:

# curl -G -k https://username:password@address_of_iCalender

>>>>>>> cfd9833df39116f351a1a4e363e5358d1ea771a4
