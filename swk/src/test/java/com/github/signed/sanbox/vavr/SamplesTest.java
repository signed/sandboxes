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
        foundNoMatchingAccountInAgora.add(251736254L);
        foundNoMatchingAccountInAgora.add(251818755L);
        foundNoMatchingAccountInAgora.add(251815003L);
        foundNoMatchingAccountInAgora.add(251796732L);
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
        meetupToAgora.put(198456464L, "Djelloul007");
        meetupToAgora.put(204051032L, "Slawa");
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

        public Long meetupId(){
            return maybeRsvpInMeetup.orElseThrow(RuntimeException::new).id;
        }

        private boolean rsvpInAgora() {
            return maybeRsvpInAgora.isPresent();
        }

        private boolean rsvpInMeetup() {
            return maybeRsvpInMeetup.isPresent();
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
        List<MeetupMember> meetup = meetupParticipants("249435342");
        List<AgoraMember> agora = agoraParticipants("ka-treffen-79");

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


        List<String[]> lines = allEventParticipants.stream().sorted(rsvpInBothSystems().thenComparing(rsvpInAgora())).map(this::toColumnsInSingleLine).collect(Collectors.toList());
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
        if(participant.rsvpInMeetup() && !meetupToAgora.containsKey(participant.meetupId()) && !foundNoMatchingAccountInAgora.contains(participant.meetupId())){
            columns.add("yes");
        }else{
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

    private String prettyPrint(String jsonString){
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
        ElementsCollection blub = Selenide.$$(".dl-horizontal > *");

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
