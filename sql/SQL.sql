-- Find product name for a wtpartnumber
select PROD.namecontainerinfo, WTPM.wtpartnumber
from pdmlinkproduct PROD, wtpartmaster WTPM
where WTPM.ida3containerreference = PROD.ida2a2
and WTPM.wtpartnumber = '0000000349'; --(wtdocnumber)


--get context for wtpart (whether it is library or product)
--select count(*) from (
select M.wtpartnumber as part_number, m.classnamekeycontainerreferen as cont_type, cont.namecontainerinfo as context, D.versionida2versioninfo as part_revision, d.iterationida2iterationinfo as part_iteration
from wtpartmaster m, wtpart d, pdmlinkproduct cont
where m.ida2a2=d.ida3masterreference
and m.ida3containerreference=cont.ida2a2
and d.latestiterationinfo=1
--;
union all
--select count(*) from (
select m.wtpartnumber as part_number, m.classnamekeycontainerreferen as cont_type, cont.namecontainerinfo as context, d.versionida2versioninfo as part_revision, d.iterationida2iterationinfo as part_iteration
from wtpartmaster m, wtpart d, wtlibrary cont
where m.ida2a2=D.ida3masterreference
and m.ida3containerreference=cont.ida2a2
and d.latestiterationinfo=1
--)
;


--get primary/secondary content filenames for each document
select M.wtdocumentnumber as docnumber, FVM.path as folder, AD.filename, AD.role, AD.createstampa2
from wtdocumentmaster M, wtdocument D, holdertocontent HC, applicationdata AD, fvitem FVI, fvfolder FVF, fvmount FVM
where M.ida2a2=D.ida3masterreference
and D.ida2a2=HC.ida3a5
and HC.ida3b5=AD.ida2a2
and AD.ida3a5=FVI.ida2a2
and FVI.ida3a4=FVF.ida2a2
and FVF.IDA2A2=FVM.IDA3A5
and d.latestiterationinfo=1
and rownum < 100;


--same as above, but limited to those created by 'wcadmin'
--**Warning, this query takes >2 min just to return 100 results**
select M.wtdocumentnumber as docnumber, FVM.path as folder, AD.filename, AD.role, AD.createstampa2
from wtdocumentmaster M, wtdocument D, holdertocontent HC, applicationdata AD, fvitem FVI, fvfolder FVF, fvmount FVM, wtuser wtu
where M.ida2a2=D.ida3masterreference
and D.ida2a2=HC.ida3a5
and HC.ida3b5=AD.ida2a2
and AD.ida3a5=FVI.ida2a2
and FVI.ida3a4=FVF.ida2a2
and FVF.IDA2A2=FVM.IDA3A5
and D.ida3d2iterationinfo=wtu.ida2a2
and wtu.name='Administrator'
and d.latestiterationinfo=1
and rownum < 100;