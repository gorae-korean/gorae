package gorae.backend.dto.client.google;

import lombok.Builder;

@Builder
public record SpaceDto(
        String name,
        String meetingUri,
        String meetingCode,
        SpaceConfig config,
        ActiveConference activeConference
) {
    public record ActiveConference(String conferenceRecord) {
    }
}
