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


# use below command to get icalendar from URL:

# curl -G -k https://username:password@address_of_iCalender

