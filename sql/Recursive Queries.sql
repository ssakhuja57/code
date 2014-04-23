with RFT(filename, famstatus, lvl, ida3a5) as
(

select distinct epmm1.cadname as G_filename, epm1.familytablestatus as G_famstatus, epmm2.cadname as I_filename, epm2.familytablestatus as I_famstatus, 1 as lvl
from epmdocument epm, epmdocumentmaster epmm, epmcontainedin ci, epmdocument epm2, epmdocumentmaster epmm2
where epm1.ida3masterreference=epmm1.ida2a2
and epmm1.cadname=ci.identifier
and ci.ida3a5=epm2.ida2a2
and epm2.ida3masterreference=epmm2.ida2a2
and epm1.familytablestatus = 2
and epm2.familytablestatus = 2

union all

select RFT.I_filename as G_filename, RFT.I_famstatus as G_famstatus, epmm2.cadname as I_filename, epm2.familytablestatus as I_famstatus, (RFT.lvl+1) as lvl
from RFT, epmcontainedin ci, epmdocument epm2, epmdocumentmaster epmm2
where RFT.I_filename=ci.identifier
and ci.ida3a5=epm2.ida2a2
and epm2.ida3masterreference=epmm2.ida2a2
and epm2.familytablestatus = 3
)

select filename, famstatus, lvl
from RFT
order by lvl desc;
