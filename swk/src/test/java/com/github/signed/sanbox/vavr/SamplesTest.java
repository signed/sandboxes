package com.github.signed.sanbox.vavr;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.freva.asciitable.AsciiTable;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.kittinunf.result.Result;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import kotlin.Triple;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class SamplesTest {

    public static <K, V> Map<V, K> invert(Map<K, V> map) {
        Map<V, K> inverted = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }
        return inverted;
    }

    private final Set<Long> foundNoMatchingAccountInAgora = new HashSet<>();

    {
        foundNoMatchingAccountInAgora.add(198200979L);
        foundNoMatchingAccountInAgora.add(221180116L);
        foundNoMatchingAccountInAgora.add(236465996L/*Not Frank Kleine*/);
        foundNoMatchingAccountInAgora.add(204225014L);
        foundNoMatchingAccountInAgora.add(184553026L);
        foundNoMatchingAccountInAgora.add(206655503L);
        foundNoMatchingAccountInAgora.add(240746008L);
        foundNoMatchingAccountInAgora.add(226997656L);
        foundNoMatchingAccountInAgora.add(219849373L);
        foundNoMatchingAccountInAgora.add(242225521L);
        foundNoMatchingAccountInAgora.add(243065615L);
        foundNoMatchingAccountInAgora.add(222786776L);
        foundNoMatchingAccountInAgora.add(251736254L);
        foundNoMatchingAccountInAgora.add(251818755L);
        foundNoMatchingAccountInAgora.add(251815003L);
        foundNoMatchingAccountInAgora.add(251796732L);
        foundNoMatchingAccountInAgora.add(236207637L);
        foundNoMatchingAccountInAgora.add(195381665L);
        foundNoMatchingAccountInAgora.add(163771392L);
        foundNoMatchingAccountInAgora.add(239177993L);
        foundNoMatchingAccountInAgora.add(183510004L);
        foundNoMatchingAccountInAgora.add(195205705L);
        foundNoMatchingAccountInAgora.add(216825493L);
        foundNoMatchingAccountInAgora.add(80870252L);
        foundNoMatchingAccountInAgora.add(238485385L);
        foundNoMatchingAccountInAgora.add(131798142L);
        foundNoMatchingAccountInAgora.add(246799655L);
        foundNoMatchingAccountInAgora.add(1169690L);
        foundNoMatchingAccountInAgora.add(203197123L);
        foundNoMatchingAccountInAgora.add(240898885L);
        foundNoMatchingAccountInAgora.add(238066131L);
        foundNoMatchingAccountInAgora.add(230877327L);
        foundNoMatchingAccountInAgora.add(233945300L);
        foundNoMatchingAccountInAgora.add(257090664L);
        foundNoMatchingAccountInAgora.add(253961105L);
        foundNoMatchingAccountInAgora.add(248575838L);
        foundNoMatchingAccountInAgora.add(236278996L);
        foundNoMatchingAccountInAgora.add(239164127L);
        foundNoMatchingAccountInAgora.add(196670328L);
        foundNoMatchingAccountInAgora.add(255103873L);
        foundNoMatchingAccountInAgora.add(235970486L);
        foundNoMatchingAccountInAgora.add(239656659L);
        foundNoMatchingAccountInAgora.add(194363805L);
        foundNoMatchingAccountInAgora.add(259990930L);
        foundNoMatchingAccountInAgora.add(260012823L);
        foundNoMatchingAccountInAgora.add(243539786L);
        foundNoMatchingAccountInAgora.add(186169813L);
        foundNoMatchingAccountInAgora.add(210237124L);
        foundNoMatchingAccountInAgora.add(252850107L);
        foundNoMatchingAccountInAgora.add(143437142L);
        foundNoMatchingAccountInAgora.add(224099539L);
        foundNoMatchingAccountInAgora.add(255593075L);
        foundNoMatchingAccountInAgora.add(226042915L);
        foundNoMatchingAccountInAgora.add(190029084L);
        foundNoMatchingAccountInAgora.add(247795838L);
        foundNoMatchingAccountInAgora.add(223745071L);
        foundNoMatchingAccountInAgora.add(264206066L);
        foundNoMatchingAccountInAgora.add(200177785L);
        foundNoMatchingAccountInAgora.add(257934440L);
        foundNoMatchingAccountInAgora.add(263331043L);
        foundNoMatchingAccountInAgora.add(244836787L);
        foundNoMatchingAccountInAgora.add(249229652L);
        foundNoMatchingAccountInAgora.add(238316094L);
        foundNoMatchingAccountInAgora.add(230085532L);
        foundNoMatchingAccountInAgora.add(266934330L);
        foundNoMatchingAccountInAgora.add(241284725L);
        foundNoMatchingAccountInAgora.add(245735146L);
        foundNoMatchingAccountInAgora.add(195639084L);
        foundNoMatchingAccountInAgora.add(271631371L);
        foundNoMatchingAccountInAgora.add(236938522L);
        foundNoMatchingAccountInAgora.add(276030206L);
        foundNoMatchingAccountInAgora.add(276049247L);
        foundNoMatchingAccountInAgora.add(276324060L);
        foundNoMatchingAccountInAgora.add(235067628L);
        foundNoMatchingAccountInAgora.add(135126322L);
        foundNoMatchingAccountInAgora.add(254333878L);
        foundNoMatchingAccountInAgora.add(268052598L);
        foundNoMatchingAccountInAgora.add(232563232L);
        foundNoMatchingAccountInAgora.add(277341485L);
        foundNoMatchingAccountInAgora.add(187323263L);
        foundNoMatchingAccountInAgora.add(213760423L);
        foundNoMatchingAccountInAgora.add(200366681L);
        foundNoMatchingAccountInAgora.add(242700846L);
        foundNoMatchingAccountInAgora.add(192438824L);
        foundNoMatchingAccountInAgora.add(281052584L);
        foundNoMatchingAccountInAgora.add(183833661L);
        foundNoMatchingAccountInAgora.add(274311244L);
        foundNoMatchingAccountInAgora.add(280715192L);
        foundNoMatchingAccountInAgora.add(272029304L);
        foundNoMatchingAccountInAgora.add(0L);
    }

    private final Map<Long, String> meetupToAgora = new HashMap<>();

    {
        meetupToAgora.put(190681184L, "Rinma");
        meetupToAgora.put(75400202L, "Nicole");
        meetupToAgora.put(239580400L, "Thomas H");
        meetupToAgora.put(239246039L, "Urs");
        meetupToAgora.put(85047252L, "jan");
        meetupToAgora.put(240845463L, "abakadaba");
        meetupToAgora.put(205068395L, "beatngu13");
        meetupToAgora.put(188418215L, "geeksogen");
        meetupToAgora.put(115087542L, "roessler");
        meetupToAgora.put(212880162L, "Mark_Treeman");
        meetupToAgora.put(198456464L, "Djelloul007");
        meetupToAgora.put(204051032L, "Slawa");
        meetupToAgora.put(240292672L, "touramakan");
        meetupToAgora.put(53511172L, "tgrip");
        meetupToAgora.put(235776797L, "Junk");
        meetupToAgora.put(182712146L, "rambii");
        meetupToAgora.put(223752698L, "pfichtner");
        meetupToAgora.put(229867812L, "Simon S");
        meetupToAgora.put(238687532L, "Corentin");
        meetupToAgora.put(68284942L, "dos");
        meetupToAgora.put(238017451L, "Wpt");
        meetupToAgora.put(279423602L, "Novi");
        meetupToAgora.put(116433192L, "johanneslink");
        meetupToAgora.put(259065935L, "TimoS"); // and error_empire
        meetupToAgora.put(0L, "");
    }

    private final Map<String, Long> agoraToMeetup = invert(meetupToAgora);

    @Rule
    public final ScreenShooter screenShooter = ScreenShooter.failedTests();

    public static class EventParticipant {
        public final Optional<AgoraMember> maybeRsvpInAgora;
        public final Optional<MeetupMember> maybeRsvpInMeetup;

        public EventParticipant(Optional<AgoraMember> agora, Optional<MeetupMember> meetup) {
            maybeRsvpInAgora = agora;
            maybeRsvpInMeetup = meetup;
        }

        public boolean rsvpInBoth() {
            return rsvpInAgora() && rsvpInMeetup();
        }

        public Long meetupId() {
            return maybeRsvpInMeetup.orElseThrow(RuntimeException::new).id;
        }

        private boolean rsvpInAgora() {
            return maybeRsvpInAgora.isPresent();
        }

        private boolean rsvpInMeetup() {
            return maybeRsvpInMeetup.isPresent();
        }

        public String name() {
            return maybeRsvpInAgora.map(it -> it.agoraFullName).orElseGet(() -> maybeRsvpInMeetup.map(it -> it.name).orElse(""));
        }
    }

    @Test
    public void meetup() {
        List<MeetupMember> meetupMembers = meetupParticipants("247284021");
        meetupMembers.forEach(System.out::println);
    }

    @Test
    public void agora() {
        List<AgoraMember> agoraMembers = agoraParticipants("ka-treffen-77");
        System.out.println(agoraMembers);
    }

    @Test
    public void combined() {
        List<MeetupMember> meetup = meetupParticipants("263759632");
        List<AgoraMember> agora = agoraParticipants("ka-treffen-95");

        printUniqueRsvps(meetup, agora);
    }

    @Test
    public void localDryRunForDiffLogic() {
        Map.Entry<String, Long> next = agoraToMeetup.entrySet().iterator().next();
        List<AgoraMember> rsvpAgora = asList(new AgoraMember(next.getKey(), "both"), new AgoraMember("agora_nickname", "Agora Only"));
        List<MeetupMember> rsvpMeetup = asList(MeetupMember.newMeetupMember(next.getValue(), "both"), MeetupMember.newMeetupMember(foundNoMatchingAccountInAgora.iterator().next(), "Meetup Only"));

        printUniqueRsvps(rsvpMeetup, rsvpAgora);
    }

    private void printUniqueRsvps(List<MeetupMember> meetup, List<AgoraMember> agora) {
        List<EventParticipant> rsvpInMeetupAndBoth = meetup.stream().map(m -> {
            Optional<AgoraMember> agoraMember = rsvpInAgora(m, agora);
            return new EventParticipant(agoraMember, Optional.of(m));
        }).collect(toList());


        List<EventParticipant> onlyRsvpInAgora = agora.stream().filter(a -> {
            Long meetupId = agoraToMeetup.getOrDefault(a.agoraId, -1L);
            return meetup.stream().noneMatch(m -> m.id.equals(meetupId));
        }).map(a -> new EventParticipant(Optional.of(a), Optional.empty())).collect(toList());

        List<EventParticipant> allEventParticipants = new ArrayList<>();
        allEventParticipants.addAll(rsvpInMeetupAndBoth);
        allEventParticipants.addAll(onlyRsvpInAgora);


        List<String[]> lines = allEventParticipants.stream()
                .sorted(rsvpInBothSystems().thenComparing(rsvpInAgora().thenComparing(byName())))
                .map(this::toColumnsInSingleLine)
                .collect(Collectors.toList());
        String[] header = {"agora name", "meetup name", "agora id", "meetup id", "mmar"};
        String[][] data = lines.toArray(new String[lines.size()][]);
        String table = AsciiTable.getTable(AsciiTable.NO_BORDERS, header, null, data);
        System.out.println("total: " + allEventParticipants.size());
        System.out.println(table);
    }

    private Comparator<EventParticipant> rsvpInAgora() {
        return new Comparator<EventParticipant>() {
            @Override
            public int compare(EventParticipant o1, EventParticipant o2) {
                if (o1.rsvpInAgora() && o2.rsvpInAgora()) {
                    return 0;
                }

                if (o1.rsvpInAgora()) {
                    return -1;
                }
                if (o2.rsvpInAgora()) {
                    return 1;
                }
                return 0;
            }
        };
    }

    private Comparator<EventParticipant> byName() {
        return new Comparator<EventParticipant>() {
            @Override
            public int compare(EventParticipant o1, EventParticipant o2) {
                return o1.name().compareTo(o2.name());
            }
        };
    }

    private Comparator<EventParticipant> rsvpInBothSystems() {
        return new Comparator<EventParticipant>() {
            @Override
            public int compare(EventParticipant o1, EventParticipant o2) {
                if (o1.rsvpInBoth() && o2.rsvpInBoth()) {
                    return 0;
                }
                if (o1.rsvpInBoth()) {
                    return -1;
                }
                if (o2.rsvpInBoth()) {
                    return 1;
                }
                return 0;
            }
        };
    }

    private String[] toColumnsInSingleLine(EventParticipant participant) {
        List<String> columns = new ArrayList<>();

        columns.add(participant.maybeRsvpInAgora.map(a -> a.agoraFullName).orElse(""));
        columns.add(participant.maybeRsvpInMeetup.map(m -> m.name).orElse(""));
        columns.add(participant.maybeRsvpInAgora.map(a -> a.agoraId).orElse(""));
        columns.add(participant.maybeRsvpInMeetup.map(m -> m.id.toString()).orElse(""));
        if (participant.rsvpInMeetup() && !meetupToAgora.containsKey(participant.meetupId()) && !foundNoMatchingAccountInAgora.contains(participant.meetupId())) {
            columns.add("yes");
        } else {
            columns.add("");
        }

        return columns.toArray(new String[columns.size()]);
    }

    private Optional<AgoraMember> rsvpInAgora(MeetupMember m, List<AgoraMember> agora) {
        String agoraId = meetupToAgora.getOrDefault(m.id, "there is no user with this id in agora");
        return agora.stream().filter(a -> a.agoraId.equals(agoraId)).findAny();
    }

    private List<MeetupMember> meetupParticipants(final String eventId) {
        Triple<Request, Response, Result<String, FuelError>> sout = Fuel.get("https://api.meetup.com/Softwerkskammer-Karlsruhe/events/" + eventId + "/rsvps?response=yes")
                .responseString();
        String jsonString = sout.component3().get();
        Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonProvider()).mappingProvider(new JacksonMappingProvider()).build();
        ParseContext using = JsonPath.using(configuration);
        DocumentContext documentContext = using.parse(jsonString);
        TypeRef<List<MeetupMember>> rsvpdMembers = new TypeRef<List<MeetupMember>>() {
        };
        return documentContext.read("$.*.[?(@.response == 'yes')]member", rsvpdMembers);
    }

    private String prettyPrint(String jsonString) {
        try {
            Object o = mapper.readValue(jsonString, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<AgoraMember> agoraParticipants(final String activityName) {

        String login = System.getenv("login");
        if (null == login) {
            throw new RuntimeException("password missing");
        }

        String password = System.getenv("password");
        if (null == password) {
            throw new RuntimeException("password missing");
        }
        screenShooter.succeededTests();

        com.codeborne.selenide.Configuration.baseUrl = "https://github.com";
        com.codeborne.selenide.Configuration.browser = WebDriverRunner.CHROME;
        Selenide.open("/login");

        $("#login_field").val(login);
        $("#password").val(password);
        $("form input[name='commit']").click();

        com.codeborne.selenide.Configuration.baseUrl = "https://www.softwerkskammer.org";
        Selenide.open("/auth/github?returnTo=%2F");
        Selenide.open("/activities/" + activityName);
        ElementsCollection blub = Selenide.$$("dl.row > *");

        List<AgoraMember> agoraMembers = new ArrayList<>(blub.size() / 2);

        for (int i = 0; i < blub.size(); i += 2) {
            SelenideElement one = blub.get(i);
            String agoraId = one.$("a").text();
            SelenideElement two = blub.get(i + 1);
            String agoraFullName = two.text().replaceAll(",.*", "");

            agoraMembers.add(new AgoraMember(agoraId, agoraFullName));
        }
        return agoraMembers;
    }

    public static class AgoraMember {

        public final String agoraId;
        public final String agoraFullName;

        public AgoraMember(String agoraId, String agoraFullName) {
            this.agoraId = agoraId;
            this.agoraFullName = agoraFullName;
        }

        @Override
        public String toString() {
            return agoraId + " " + agoraFullName;
        }
    }

    ObjectMapper mapper = new ObjectMapper();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MeetupMember {

        public static MeetupMember newMeetupMember(Long id, String name) {
            MeetupMember meetupMember = new MeetupMember();
            meetupMember.id = id;
            meetupMember.name = name;
            return meetupMember;
        }

        public Long id;
        public String name;

        @Override
        public String toString() {
            return id + " " + name;
        }
    }
}
