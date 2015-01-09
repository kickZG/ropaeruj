1) Algoritam radi dobro za n = 10, nalazi rješenje i unutar 30-ak generacija.
2) Već za n = 100 algoritam zapinje na "platou", pokušao sam s povećanjem broja
	presjeka kod križanja, raznim brojevima za kTournament (2, 3, 5 - svejedno
	zapinje već na početnim generacijama).
3) Za n = 1000 rješenja su cijelo vrijeme jako loša (oko 0.6), bolje je situacija
	kad se odabere manji broj roditelja za križanje (npr. 2) te kada se radi s
	manjim generacijama i većim brojem presjeka kod križanja.