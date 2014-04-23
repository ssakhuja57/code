select distinct epmm1.cadname as G_filename, epmm2.cadname as I_filename
from epmdocument epm1, epmdocumentmaster epmm1, epmsepfamilytablemaster ftm1, epmsepfamilytable ft1, epmcontainedin ci, epmdocument epm2, epmdocumentmaster epmm2
where epm1.ida3masterreference=epmm1.ida2a2
and epmm1.cadname=ftm1.name
and ftm1.ida2a2=ft1.ida3masterreference
and ft1.ida2a2=ci.ida3b5
and ci.ida3a5=epm2.ida2a2
and epm2.ida3masterreference=epmm2.ida2a2
and epm2.familytablestatus <> 2
order by epmm1.cadname;
