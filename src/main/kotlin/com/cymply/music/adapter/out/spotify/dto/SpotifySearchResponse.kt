package com.cymply.music.adapter.out.spotify.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpotifySearchResponse(
    val tracks: Track
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Track(
        val items: List<Item>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Item(
        val name: String,
        val album: Album,
        val artists: List<Artist>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Album(
        val name: String,
        val images: List<Image>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Artist(
        val name: String
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Image(
        val url: String,
        val height: Int
    )
}

/*
{
  "tracks": {
    "href": "https://api.spotify.com/v1/search?offset=0&limit=1&query=%EC%8F%9C%EC%95%A0%ED%94%8C&type=track&locale=ko-KR,ko;q%3D0.9,en-US;q%3D0.8,en;q%3D0.7",
    "limit": 1,
    "next": "https://api.spotify.com/v1/search?offset=1&limit=1&query=%EC%8F%9C%EC%95%A0%ED%94%8C&type=track&locale=ko-KR,ko;q%3D0.9,en-US;q%3D0.8,en;q%3D0.7",
    "offset": 0,
    "previous": null,
    "total": 45,
    "items": [
      {
        "album": {
          "album_type": "album",
          "total_tracks": 10,
          "available_markets": ["AR", "AU", "AT", "BE", "BO", "BR", "BG", "CA", "CL", "CO", "CR", "CY", "CZ", "DK", "DO", "DE", "EC", "EE", "SV", "FI", "FR", "GR", "GT", "HN", "HK", "HU", "IS", "IE", "IT", "LV", "LT", "LU", "MY", "MT", "MX", "NL", "NZ", "NI", "NO", "PA", "PY", "PE", "PH", "PL", "PT", "SG", "SK", "ES", "SE", "CH", "TW", "TR", "UY", "US", "GB", "AD", "LI", "MC", "ID", "JP", "TH", "VN", "RO", "IL", "ZA", "SA", "AE", "BH", "QA", "OM", "KW", "EG", "MA", "DZ", "TN", "LB", "JO", "PS", "IN", "BY", "KZ", "MD", "UA", "AL", "BA", "HR", "ME", "MK", "RS", "SI", "KR", "BD", "PK", "LK", "GH", "KE", "NG", "TZ", "UG", "AG", "AM", "BS", "BB", "BZ", "BT", "BW", "BF", "CV", "CW", "DM", "FJ", "GM", "GE", "GD", "GW", "GY", "HT", "JM", "KI", "LS", "LR", "MW", "MV", "ML", "MH", "FM", "NA", "NR", "NE", "PW", "PG", "PR", "WS", "SM", "ST", "SN", "SC", "SL", "SB", "KN", "LC", "VC", "SR", "TL", "TO", "TT", "TV", "VU", "AZ", "BN", "BI", "KH", "CM", "TD", "KM", "GQ", "SZ", "GA", "GN", "KG", "LA", "MO", "MR", "MN", "NP", "RW", "TG", "UZ", "ZW", "BJ", "MG", "MU", "MZ", "AO", "CI", "DJ", "ZM", "CD", "CG", "IQ", "LY", "TJ", "VE", "ET", "XK"],
          "external_urls": {
            "spotify": "https://open.spotify.com/album/6h8CGwYbH7wW4XfcwKNzNe"
          },
          "href": "https://api.spotify.com/v1/albums/6h8CGwYbH7wW4XfcwKNzNe",
          "id": "6h8CGwYbH7wW4XfcwKNzNe",
          "images": [
            {
              "url": "https://i.scdn.co/image/ab67616d0000b2730b1e2a5d990c3e198effa85b",
              "height": 640,
              "width": 640
            },
            {
              "url": "https://i.scdn.co/image/ab67616d00001e020b1e2a5d990c3e198effa85b",
              "height": 300,
              "width": 300
            },
            {
              "url": "https://i.scdn.co/image/ab67616d000048510b1e2a5d990c3e198effa85b",
              "height": 64,
              "width": 64
            }
          ],
          "name": "이상기후",
          "release_date": "2014-06-12",
          "release_date_precision": "day",
          "type": "album",
          "uri": "spotify:album:6h8CGwYbH7wW4XfcwKNzNe",
          "artists": [
            {
              "external_urls": {
                "spotify": "https://open.spotify.com/artist/6S4fsREHT1NEjTb3lYD2pG"
              },
              "href": "https://api.spotify.com/v1/artists/6S4fsREHT1NEjTb3lYD2pG",
              "id": "6S4fsREHT1NEjTb3lYD2pG",
              "name": "THORNAPPLE",
              "type": "artist",
              "uri": "spotify:artist:6S4fsREHT1NEjTb3lYD2pG"
            }
          ],
          "is_playable": true
        },
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/6S4fsREHT1NEjTb3lYD2pG"
            },
            "href": "https://api.spotify.com/v1/artists/6S4fsREHT1NEjTb3lYD2pG",
            "id": "6S4fsREHT1NEjTb3lYD2pG",
            "name": "THORNAPPLE",
            "type": "artist",
            "uri": "spotify:artist:6S4fsREHT1NEjTb3lYD2pG"
          }
        ],
        "available_markets": ["AR", "AU", "AT", "BE", "BO", "BR", "BG", "CA", "CL", "CO", "CR", "CY", "CZ", "DK", "DO", "DE", "EC", "EE", "SV", "FI", "FR", "GR", "GT", "HN", "HK", "HU", "IS", "IE", "IT", "LV", "LT", "LU", "MY", "MT", "MX", "NL", "NZ", "NI", "NO", "PA", "PY", "PE", "PH", "PL", "PT", "SG", "SK", "ES", "SE", "CH", "TW", "TR", "UY", "US", "GB", "AD", "LI", "MC", "ID", "JP", "TH", "VN", "RO", "IL", "ZA", "SA", "AE", "BH", "QA", "OM", "KW", "EG", "MA", "DZ", "TN", "LB", "JO", "PS", "IN", "BY", "KZ", "MD", "UA", "AL", "BA", "HR", "ME", "MK", "RS", "SI", "KR", "BD", "PK", "LK", "GH", "KE", "NG", "TZ", "UG", "AG", "AM", "BS", "BB", "BZ", "BT", "BW", "BF", "CV", "CW", "DM", "FJ", "GM", "GE", "GD", "GW", "GY", "HT", "JM", "KI", "LS", "LR", "MW", "MV", "ML", "MH", "FM", "NA", "NR", "NE", "PW", "PG", "PR", "WS", "SM", "ST", "SN", "SC", "SL", "SB", "KN", "LC", "VC", "SR", "TL", "TO", "TT", "TV", "VU", "AZ", "BN", "BI", "KH", "CM", "TD", "KM", "GQ", "SZ", "GA", "GN", "KG", "LA", "MO", "MR", "MN", "NP", "RW", "TG", "UZ", "ZW", "BJ", "MG", "MU", "MZ", "AO", "CI", "DJ", "ZM", "CD", "CG", "IQ", "LY", "TJ", "VE", "ET", "XK"],
        "disc_number": 1,
        "duration_ms": 293210,
        "explicit": false,
        "external_ids": {
          "isrc": "KRB621400043"
        },
        "external_urls": {
          "spotify": "https://open.spotify.com/track/4644JaCBBX7EpiZY1XXJaW"
        },
        "href": "https://api.spotify.com/v1/tracks/4644JaCBBX7EpiZY1XXJaW",
        "id": "4644JaCBBX7EpiZY1XXJaW",
        "is_playable": true,
        "name": "시퍼런 봄",
        "popularity": 45,
        "preview_url": null,
        "track_number": 2,
        "type": "track",
        "uri": "spotify:track:4644JaCBBX7EpiZY1XXJaW",
        "is_local": false
      }
    ]
  }
}
 */